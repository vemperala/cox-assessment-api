package com.assessment.cox.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by akhilesh on 1/31/20.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class StoreDTO implements Serializable {
  private Long id;
  private String type;
  private String name;
  private String address;
  private String address2;
  private String city;
  private String state;
  private String zip;
  private LocationDTO location;
  private String hours;
  @Builder.Default
  private List<String> services = new ArrayList<>();
}

