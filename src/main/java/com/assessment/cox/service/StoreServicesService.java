package com.assessment.cox.service;

import com.assessment.cox.entity.Services;
import com.assessment.cox.repository.ServiceRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by akhilesh on 2/2/20.
 */
@Service
public class StoreServicesService {

  @Autowired
  private ServiceRepo serviceRepo;

  public List<Services> getAllMatchedServices(List<String> matchedServices){
   return serviceRepo.findByServiceNameIn(matchedServices);
  }

  public List<Services> getAllServices(){
   return serviceRepo.findAll();
  }
}
