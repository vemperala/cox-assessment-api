package com.assessment.cox.entity;

import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The persistent class for the services database table.
 *
 */
@Entity
@Table(name="services")
@Data
@NoArgsConstructor
@AllArgsConstructor
@NamedQuery(name="Service.findAll", query="SELECT s FROM Services s")
public class Services implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, unique = true, updatable = false)
  private Long id;
  @Column(name="service_name")
  private String serviceName;


  //bi-directional many-to-one association to StoreServices
/*  @OneToMany(mappedBy="service")
  private List<StoreServices> storeServices;

  public StoreServices addStoreService(StoreServices storeService) {
    getStoreServices().add(storeService);
    storeService.setService(this);
    return storeService;
  }
  public StoreServices removeStoreService(StoreServices storeService) {
    getStoreServices().remove(storeService);
    storeService.setService(null);
    return storeService;
  }*/
}
