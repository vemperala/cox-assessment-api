package com.assessment.cox.dto;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by akhilesh on 2/1/20.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LocationDTO {

  private BigDecimal lat;
  private BigDecimal lon;
}
