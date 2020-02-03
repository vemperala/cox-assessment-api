package com.assessment.cox.resource;

import com.assessment.cox.dto.StoreDTO;
import com.assessment.cox.entity.Store;
import com.assessment.cox.service.StoresService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by akhilesh on 2/2/20.
 */

@RestController
public class StoreResource {

  @Autowired
  private StoresService storesService;

  @GetMapping(value = "/store/list", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<StoreDTO> getAllStores(){
    return storesService.getAllStores();
  }

  @GetMapping(value = "/store/id/{storeId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public StoreDTO getStoreById(@PathVariable Long  storeId){
    System.out.println(" ************************   "+storeId);
    return storesService.findStoreByStoreId(storeId);
  }

  @PutMapping(value = "/store/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public StoreDTO updateStore(@RequestBody StoreDTO  storeDTO){
    System.out.println(" ************************   "+storeDTO.getId());
    return storesService.updateStore(storeDTO);
  }

  @DeleteMapping(value = "/store/delete/{storeId}", produces = MediaType.APPLICATION_JSON_VALUE)
  public StoreDTO deleStore(@PathVariable Long storeId){
    return storesService.deleteStore(storeId);
  }
}
