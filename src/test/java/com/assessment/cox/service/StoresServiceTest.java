package com.assessment.cox.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import com.assessment.cox.dto.LocationDTO;
import com.assessment.cox.dto.StoreDTO;
import com.assessment.cox.entity.DaysOfWeek;
import com.assessment.cox.entity.Location;
import com.assessment.cox.entity.Services;
import com.assessment.cox.entity.Store;
import com.assessment.cox.entity.StoreHour;
import com.assessment.cox.repository.ServiceRepo;
import com.assessment.cox.repository.StoreRepo;
public class StoresServiceTest {
	@Mock
	private StoreRepo storeRepo;
	@Mock
	  private ServiceRepo serviceRepo;
	  @InjectMocks
	  StoresService service;
	  @Before
	  public void init() {
	    MockitoAnnotations.initMocks(this);
	  }
	  @Test
	  public void testSaveAll() {
		  when(storeRepo.saveAll(Mockito.anySet())).thenReturn(Arrays.asList(createStore()));
		  List<StoreDTO> allStores = Arrays.asList(createStoreDTO());
		     String[] services = { "Best Buy Mobile", "Samsung Experience Shop"};
		  List<Store> stores = service.saveAll(allStores, Arrays.asList(services));
		  assertTrue(stores.size()==1);
	  }
	  @Test
	  public void testbuildStoreTobeSaved() {
		  Store store = new Store();
		  store = service.buildStoreTobeSaved(createStoreDTO(), store);
		 assertNotNull(store.getLocation());
		 assertEquals(store.getZip(),"54467");
	  }
	  @Test
	  public void testGetStoreHours() {
		  List<StoreHour> storeHours = service.getStoreHours("Mon: 10-9:30; Tue: 10-9:30; Wed: 10-9:30; Thurs: 10-9:30; Fri: 10-9:30; Sat: 10-9:30; Sun: 11-8",null);
		  assertThat(storeHours.size(), is(7));
		  assertEquals(storeHours.get(0).getDay(),"MONDAY");
	  }
	  @Test
	  public void testGenerateStoreDTO() {
		  StoreDTO store = service.generateStoreDTO(createStore());
		  assertThat(store.getServices().size(), is(1));
		  assertNotNull(store.getHours());
	  }
	  public Store createStore() {
		  Store store = new Store();
		  StoreDTO s = createStoreDTO();
		  store.setId(s.getId());
		    store.setType(s.getType());
		    store.setName("Arundel Mills");
		    store.setAddress("7000 Arundel Mills Circle");
		    store.setAddress2(s.getAddress2());
		    store.setCity(s.getCity());
		    store.setState(s.getState());
		    store.setZip(s.getZip());
		    Location location = new Location();
		    location.setLattitude(s.getLocation().getLat());
		    location.setLongitude(s.getLocation().getLon());
		    location.setStore(store);
		    store.setLocation(location);
		    StoreHour storeHour = new StoreHour();
		    storeHour.setDay(DaysOfWeek.Mon.namespace());
		    storeHour.setOpenTime(Instant.EPOCH.atZone(ZoneOffset.UTC)
		              .withHour(10).withMinute(0).withSecond(0).toInstant());
		    storeHour.setCloseTime(Instant.EPOCH.atZone(ZoneOffset.UTC)
		              .withHour(9).withMinute(0).withSecond(0).toInstant());
		    List<StoreHour>  storeHours = Arrays.asList();
		    store.setStoreHours(storeHours);
		    Services service = new Services();
		    service.setServiceName("Best Buy Mobile");
		    List<Services> storeServices = Arrays.asList(service);
		    store.setStoreServices(storeServices);
		    return store;
	  }
	  public StoreDTO createStoreDTO() {
			StoreDTO store = new StoreDTO();
		     store.setCity("Plover");
		     store.setAddress("1145 Commons Cir");
		     store.setName("Stevens Point");
		     store.setType("BigBox");
		     store.setState("WI");
		     store.setZip("54467");
		     LocationDTO location = new LocationDTO();
		     location.setLat(new BigDecimal(44.522335));
		     location.setLon(new BigDecimal(-89.515335));
		     store.setLocation(location);
		     store.setHours("Mon: 10-9:30; Tue: 10-9:30; Wed: 10-9:30; Thurs: 10-9:30; Fri: 10-9:30; Sat: 10-9:30; Sun: 11-8");
		     String[] services = { "Best Buy Mobile", "Samsung Experience Shop"};
		     store.setServices(Arrays.asList(services));
		     return store;
		}
}