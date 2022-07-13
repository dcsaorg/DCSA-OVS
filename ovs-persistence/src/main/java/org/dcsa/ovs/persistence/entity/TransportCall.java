package org.dcsa.ovs.persistence.entity;

import lombok.*;
import org.dcsa.ovs.persistence.entity.enums.PortCallStatusCode;
import org.dcsa.skernel.domain.persistence.entity.Facility;
import org.dcsa.skernel.domain.persistence.entity.Location;

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
@Table(name = "transport_call")
public class TransportCall {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "transport_call_reference", length = 100, nullable = false)
  private String transportCallReference;

  @Column(name = "transport_call_sequence_number")
  private Integer transportCallSequenceNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "facility_id")
  private Facility facility;

  @Column(name = "facility_type_code", length = 4, columnDefinition = "bpchar")
  private String facilityTypeCode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "location_id")
  private Location location;

  @Column(name = "mode_of_transport_code", length = 3)
  private String modeOfTransportCode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vessel_id")
  private Vessel vessel;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "import_voyage_id")
  private Voyage importVoyage;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "export_voyage_id")
  private Voyage exportVoyage;

  @Enumerated(EnumType.STRING)
  @Column(name = "port_call_status_code", length = 4, columnDefinition = "bpchar")
  private PortCallStatusCode portCallStatusCode;

  @Column(name="port_visit_reference", length=50)
  private String portVisitReference;

  @EqualsAndHashCode.Exclude
  @OneToMany
  @JoinColumn(name="transport_call_id")
  private Set<TransportEvent> timestamps = new LinkedHashSet<>();
}
