package com.assessment.cox.entity;

import java.io.Serializable;
import java.util.Set;
import javax.persistence.*;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

/**
 * The persistent class for the store database table.
 *
 */
@Entity
@Table(name="store")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude= {"location", "storeHours","storeServices"})
@NamedQuery(name="Store.findAll", query="SELECT s FROM Store s")
public class Store implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GenericGenerator(
      name = "custom-identity",
      strategy = "com.assessment.cox.repository.strategy.CustomIdentityGenerator"
  )
  @GeneratedValue(
      generator = "custom-identity",
      strategy = GenerationType.IDENTITY
  )
  @Column(unique = true, nullable = false)
  private Long id;
  private String address;
  private String address2;
  private String city;
  private String name;
  private String state;
  private String type;
  private String zip;


  //bi-directional many-to-one association to Location
  @OneToOne(mappedBy="store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Location location;



  //bi-directional many-to-one association to StoreHour
  @OneToMany(mappedBy="store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private List<StoreHour> storeHours;



  //bi-directional many-to-one association to StoreServices
  //@OneToMany(mappedBy="store")
  @OneToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "store_services",
      inverseJoinColumns = {@JoinColumn(table = "services", name = "service_ref", referencedColumnName = "id")},
      joinColumns = {@JoinColumn(table = "store", name = "store_ref", referencedColumnName = "id")})
  private List<Services> storeServices;

  public StoreHour addStoreHour(StoreHour storeHour) {
    getStoreHours().add(storeHour);
    storeHour.setStore(this);
    return storeHour;
  }
  public StoreHour removeStoreHour(StoreHour storeHour) {
    getStoreHours().remove(storeHour);
    storeHour.setStore(null);
    return storeHour;
  }
  public void removeAllStoreHour(List<StoreHour> storehours) {
    storehours.forEach(storeHour -> {
      this.storeHours.remove(storeHour);
      storeHour.setStore(null);
    });
  }
}


