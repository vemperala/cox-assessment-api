package com.assessment.cox.resource;

import com.assessment.cox.dto.StoreDTO;
import com.assessment.cox.entity.Services;
import com.assessment.cox.service.StoresService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by akhilesh on 1/31/20.
 */
@RestController
public class LoadData {

  @Autowired
  private StoresService storesService;

  @PostMapping(value="/load/data", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<StoreDTO> loadSampleDataIntoDb(@RequestBody List<StoreDTO> storeDto){
    List<String> distinctServices = storeDto.stream().map(StoreDTO::getServices).filter(s -> s!=null && !s.isEmpty()).flatMap(List::stream).distinct().collect(
        Collectors.toList());
    storesService.saveAll(storeDto, distinctServices);
    return storeDto;
  }

  @PostMapping(value="/test/service", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Services> checkServices(@RequestBody List<String> storeDto){

    return storesService.getService(storeDto);
  }
}
