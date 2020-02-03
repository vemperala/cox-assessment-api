package com.assessment.cox.entity;
import java.io.Serializable;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * The persistent class for the store_services database table.
 *
 */
@Entity
@Table(name="store_services")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude="store")
@NamedQuery(name="StoreService.findAll", query="SELECT s FROM StoreServices s")
public class StoreServices implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, unique = true, updatable = false)
  private String id;
  //bi-directional many-to-one association to Store
  @ManyToOne
  @JoinColumn(name="store_ref")
  private Store store;
  //bi-directional many-to-one association to Service
  @ManyToOne
  @JoinColumn(name="service_ref")
  private Services service;
}
