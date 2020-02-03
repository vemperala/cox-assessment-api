package com.assessment.cox.repository;

import com.assessment.cox.entity.StoreHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by akhilesh on 2/1/20.
 */
@Repository
public interface StoreHourRepo extends JpaRepository<StoreHour, Long>{

}
