package com.assessment.cox.repository;

import com.assessment.cox.entity.Services;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by akhilesh on 2/1/20.
 */
@Repository
public interface ServiceRepo extends JpaRepository<Services, Long>{

  List<Services> findByServiceNameIn(List<String> serviceName);

}
