package com.assessment.cox.service;

import com.assessment.cox.dto.LocationDTO;
import com.assessment.cox.dto.StoreDTO;
import com.assessment.cox.entity.DaysOfWeek;
import com.assessment.cox.entity.Location;
import com.assessment.cox.entity.Services;
import com.assessment.cox.entity.Store;
import com.assessment.cox.entity.StoreHour;
import com.assessment.cox.exception.StoreApiException;
import com.assessment.cox.repository.ServiceRepo;
import com.assessment.cox.repository.StoreRepo;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by akhilesh on 2/2/20.
 */
@org.springframework.stereotype.Service
public class StoresService {
  @Autowired
  private StoreRepo storeRepo;

  @Autowired
  private ServiceRepo serviceRepo;


  public List<Store> saveAll(List<StoreDTO> storeDTO, List<String> distinctServices){


    Set<Store> storesToBeSaved = new HashSet<>();

    storeDTO.forEach(s -> {
      Store store = new Store();
      storesToBeSaved.add(buildStoreTobeSaved(s, store));
    });

    List<Store> store = storeRepo.saveAll(storesToBeSaved);
    return store;
  }


  public StoreDTO createNewStore(StoreDTO storeDTO){
    Store storeToBeSaved = new Store();
    Store store = storeRepo.save(buildStoreTobeSaved(storeDTO, storeToBeSaved));
    return generateStoreDTO(store);
  }

  public Store buildStoreTobeSaved(StoreDTO s, Store store){

    store.setId(s.getId());
    store.setType(s.getType());
    store.setName(s.getName());
    store.setAddress(s.getAddress());
    store.setAddress2(s.getAddress2());
    store.setCity(s.getCity());
    store.setState(s.getState());
    store.setZip(s.getZip());
    store.setStoreServices(servicesToSaveOrUpdate(s));
    store.setStoreHours(getStoreHours(s.getHours(), store));
    Location location = new Location();
    location.setLattitude(s.getLocation().getLat());
    location.setLongitude(s.getLocation().getLon());
    location.setStore(store);
    store.setLocation(location);
    return store;
  }

  private List<StoreHour> getStoreHours(String hours, Store store){
    List<StoreHour> storehours = new ArrayList<>();

    Arrays.asList(hours.split(";")).forEach(e -> {
      String[] data = e.split(":",2);
      String[] times = data[1].split("-");
      String opentime =  times[0].trim();
      String closeTime = times[1].trim();
      StoreHour hour = new StoreHour();
      hour.setDay(DaysOfWeek.valueOf(data[0].trim()).namespace());
      hour.setOpenTime(Instant.EPOCH.atZone(ZoneOffset.UTC)
              .withHour(Integer.parseInt(opentime.contains(":")?opentime.substring(0,opentime.indexOf(":")):opentime))
              .withMinute(Integer.parseInt(opentime.contains(":")?opentime.substring(opentime.indexOf(":")+1):"0"))
              .withSecond(0)
              .toInstant());
      hour.setCloseTime(Instant.EPOCH.atZone(ZoneOffset.UTC)
              .withHour(Integer.parseInt(closeTime.contains(":")?closeTime.substring(0,closeTime.indexOf(":")):closeTime))
              .withMinute(Integer.parseInt(closeTime.contains(":")?closeTime.substring(closeTime.indexOf(":")+1):"0"))
              .withSecond(0)
              .toInstant());
      hour.setStore(store);
      storehours.add(hour);
    });

    return storehours;
  }


  @Transactional(readOnly = true)
  public List<StoreDTO> getAllStores(){
    List<Store> storeList = storeRepo.findAll();
    List<StoreDTO> storeDTOList = new ArrayList<>();
    storeList.forEach(e ->{
      storeDTOList.add(generateStoreDTO(e));
    });
    return storeDTOList;
  }

  @Transactional(readOnly = true)
  public StoreDTO findStoreByStoreId(Long storeId) {
      Optional<Store> store =  storeRepo.findById(storeId);
      if(!store.isPresent()){
        throw new  StoreApiException(400, "unable to find the store for the store id:"+storeId);
      }
    return generateStoreDTO(store.get());
  }


  private StoreDTO generateStoreDTO(Store store){

    List<StoreHour> storeHourList = store.getStoreHours();
    StringBuilder stringBuilder = new StringBuilder();
    storeHourList.forEach(
        e -> {
          if (stringBuilder.length() <= 0) stringBuilder.append("");
          else stringBuilder.append("; ");

          stringBuilder.append("").append(DaysOfWeek.valueOf(e.getDay()).day()).append(": ");

          if (e.getOpenTime() != null && e.getOpenTime().atOffset(ZoneOffset.UTC).getHour() > 0) {
            stringBuilder.append(e.getOpenTime().atOffset(ZoneOffset.UTC).getHour());
          } else {
            stringBuilder.append(0);
          }
          if (e.getOpenTime() != null && e.getOpenTime().atOffset(ZoneOffset.UTC).getMinute() > 0) {
            stringBuilder.append(":").append(e.getOpenTime().atOffset(ZoneOffset.UTC).getMinute());
          }
          stringBuilder.append("-");
          if (e.getCloseTime() != null && e.getCloseTime().atOffset(ZoneOffset.UTC).getHour() > 0) {
            stringBuilder.append(e.getCloseTime().atOffset(ZoneOffset.UTC).getHour());
          } else {
            stringBuilder.append(0);
          }
          if (e.getCloseTime() != null && e.getCloseTime().atOffset(ZoneOffset.UTC).getMinute() > 0) {
            stringBuilder.append(":").append(e.getCloseTime().atOffset(ZoneOffset.UTC).getMinute());
          }
        });

    return StoreDTO.builder()
        .id(store.getId())
        .type(store.getType())
        .name(store.getName())
        .address(store.getAddress())
        .address2(store.getAddress2())
        .city(store.getCity())
        .state(store.getState())
        .zip(store.getZip())
        .location(LocationDTO.builder().lat(store.getLocation().getLattitude()).lon(store.getLocation().getLongitude()).build())
        .services(store.getStoreServices().stream().map(Services::getServiceName).collect(Collectors.toList()))
        .hours(stringBuilder.toString())
        .build();
  }


  @Transactional
  public StoreDTO updateStore(StoreDTO storeDTO){
    Optional<Store> store = storeRepo.findById(storeDTO.getId());
    if(!store.isPresent()){
      throw new  StoreApiException(400, "unable to find the store for the store id:"+storeDTO.getId());
    }
    Store storeToUpdate = buildStoreToUpdate(storeDTO, store.get());
    return generateStoreDTO(storeRepo.save(storeToUpdate));
  }

  private Store buildStoreToUpdate(StoreDTO updateStoreDTO, Store storeToUpdate){


    storeToUpdate.setType(updateStoreDTO.getType());
    storeToUpdate.setName(updateStoreDTO.getName());
    storeToUpdate.setAddress(updateStoreDTO.getAddress());
    storeToUpdate.setAddress2(updateStoreDTO.getAddress2());
    storeToUpdate.setCity(updateStoreDTO.getCity());
    storeToUpdate.setState(updateStoreDTO.getState());
    storeToUpdate.setZip(updateStoreDTO.getZip());
    storeToUpdate.setStoreServices(servicesToSaveOrUpdate(updateStoreDTO));
    storeToUpdate.setStoreServices(servicesToSaveOrUpdate(updateStoreDTO));
    List<StoreHour> storeHours = new CopyOnWriteArrayList<>();
    storeHours.addAll(storeToUpdate.getStoreHours());
    storeToUpdate.removeAllStoreHour(storeHours);
    getStoreHours(updateStoreDTO.getHours(),storeToUpdate).forEach(storeHour->storeToUpdate.addStoreHour(storeHour));
    storeToUpdate.setStoreHours(getStoreHours(updateStoreDTO.getHours(), storeToUpdate));
    storeToUpdate.getLocation().setLattitude(updateStoreDTO.getLocation().getLat());
    storeToUpdate.getLocation().setLongitude(updateStoreDTO.getLocation().getLon());

    return storeToUpdate;
  }

  @Transactional
  public StoreDTO deleteStore(Long storeId){
    Optional<Store> store = storeRepo.findById(storeId);
    if(!store.isPresent()){
      throw new StoreApiException(400,"unable to find the store for the store id:"+storeId);
    }
    storeRepo.deleteById(storeId);
    return generateStoreDTO(store.get());
  }

  public List<Services> getService(List<String> strings){
    return serviceRepo.findByServiceNameIn(strings);
  }

  private List<Services> servicesToSaveOrUpdate(StoreDTO storeDTO){

    Map<String, Services> servicesMap = serviceRepo.findByServiceNameIn(storeDTO.getServices()).stream().collect(Collectors.toMap(x-> x.getServiceName(), x->x));
    List<Services> matchedServicesList = new ArrayList<>();
    List<Services> unmatchedServiceNameList = new ArrayList<>();
    storeDTO.getServices().forEach(e-> {
      if(Objects.nonNull(servicesMap) && servicesMap.containsKey(e)){
        matchedServicesList.add(servicesMap.get(e));
      }else{

        Services services = new Services();
        services.setServiceName(e);
        unmatchedServiceNameList.add(services);
      }
    });
    if(!unmatchedServiceNameList.isEmpty()){
      List<Services> newlyAddedServiceList = serviceRepo.saveAll(unmatchedServiceNameList);
      matchedServicesList.addAll(newlyAddedServiceList);
    }
  return matchedServicesList;
  }

}
