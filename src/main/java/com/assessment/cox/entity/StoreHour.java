package com.assessment.cox.entity;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * The persistent class for the store_hours database table.
 *
 */
@Entity
@Table(name="store_hours")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude="store")
@NamedQuery(name="StoreHour.findAll", query="SELECT s FROM StoreHour s")
public class StoreHour implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, unique = true, updatable = false)
  private Long id;
  private String day;
  @Column(name="close_time")
  private Instant closeTime;
  @Column(name="open_time")
  private Instant openTime;
  //bi-directional many-to-one association to Store
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name="store_ref")
  private Store store;
}
