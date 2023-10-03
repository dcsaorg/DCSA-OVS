package org.dcsa.ovs.persistence.entity;

import lombok.*;
import org.dcsa.skernel.domain.persistence.entity.Carrier;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@NamedEntityGraph(
    name = "graph.vessels",
    attributeNodes = {
      @NamedAttributeNode(value = "vessels", subgraph = "subgraph.transportCalls")
    },
    subgraphs = {
      @NamedSubgraph(
          name = "subgraph.transportCalls",
          attributeNodes = {
            @NamedAttributeNode(value = "transportCalls", subgraph = "subgraph.timestamps")
          }),
      @NamedSubgraph(
          name = "subgraph.timestamps",
          attributeNodes = {@NamedAttributeNode(value = "timestamps")})
    })
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "service")
public class Service {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "carrier_id")
  private Carrier carrier;

  @Column(name = "carrier_service_code", length = 11)
  private String carrierServiceCode;

  @Column(name = "carrier_service_name", length = 50)
  private String carrierServiceName;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY,cascade = {CascadeType.PERSIST, CascadeType.MERGE})
  @JoinColumn(name = "tradelane_id")
  private Tradelane tradelane;

  @Column(name = "universal_service_reference", length = 8)
  private String universalServiceReference;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(
      name = "vessel_schedule",
      joinColumns = @JoinColumn(name = "service_id"),
      inverseJoinColumns = @JoinColumn(name = "vessel_id"))
  private Set<Vessel> vessels = new LinkedHashSet<>();
}
