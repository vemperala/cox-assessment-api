package com.assessment.cox.resource;

import com.assessment.cox.entity.Services;
import com.assessment.cox.service.ServicesService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by akhilesh on 2/2/20.
 */

@RestController
public class ServiceResource {

  @Autowired
  private ServicesService servicesService;

  @PostMapping(value="/services/match", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Services> getAllServices(@RequestBody List<String> serviceNames){
    return servicesService.getAllMatchedServices(serviceNames);
  }

  @GetMapping(value="/services/list", produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Services> getAllServices(){
    return servicesService.getAllServices();
  }

}
