package com.assessment.cox.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import lombok.EqualsAndHashCode;

/**
 * The persistent class for the location database table.
 *
 */
@Entity
@Table(name="location")
@EqualsAndHashCode(exclude="store")
@NamedQuery(name="Location.findAll", query="SELECT l FROM Location l")
public class Location implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, unique = true, updatable = false)
  private Long id;
  private BigDecimal lattitude;
  private BigDecimal longitude;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="store_ref")
  private Store store;

  public Location() {
  }

  public Location(BigDecimal lattitude, BigDecimal longitude, Store store) {
    this.lattitude = lattitude;
    this.longitude = longitude;
    this.store = store;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public BigDecimal getLattitude() {
    return lattitude;
  }

  public void setLattitude(BigDecimal lattitude) {
    this.lattitude = lattitude;
  }

  public BigDecimal getLongitude() {
    return longitude;
  }

  public void setLongitude(BigDecimal longitude) {
    this.longitude = longitude;
  }

  public Store getStore() {
    return store;
  }

  public void setStore(Store store) {
    this.store = store;
  }
}
