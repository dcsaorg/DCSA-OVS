package org.dcsa.ovs.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "vessel")
public class Vessel {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "vessel_imo_number", length = 7, unique = true)
  private String imoNumber;

  @Column(name = "vessel_name", length = 35)
  private String name;

  @Column(name = "vessel_flag", length = 2, columnDefinition = "bpchar")
  private String flag;

  @Column(name = "vessel_call_sign", length = 18)
  private String callSign;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vessel_operator_carrier_id")
  private Carrier operatorCarrier;

  @Column(name = "is_dummy")
  private Boolean isDummy;

  @Column(name = "length", columnDefinition = "numeric")
  private Float length;

  @Column(name = "width", columnDefinition = "numeric")
  private Float width;

  @Column(name = "dimension_unit", length = 3)
  private String dimensionUnit;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "vessel")
  private Set<TransportCall> portCalls = new LinkedHashSet<>();
}
