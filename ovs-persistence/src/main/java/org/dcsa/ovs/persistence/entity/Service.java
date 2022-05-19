package org.dcsa.ovs.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "service")
public class Service {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "carrier_id")
  private Carrier carrier;

  @Column(name = "carrier_service_code", length = 5)
  private String carrierServiceCode;

  @Column(name = "carrier_service_name", length = 50)
  private String carrierServiceName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "tradelane_id")
  private Tradelane tradelane;

  @Column(name = "universal_service_reference", length = 8)
  private String universalServiceReference;

  @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinTable(
      name = "vessel_schedule",
      joinColumns = @JoinColumn(name = "service_id"),
      inverseJoinColumns = @JoinColumn(name = "vessel_id"))
  List<Vessel> vessels;
}
/*
CREATE TABLE dcsa_im_v3_0.service (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    carrier_id uuid NULL REFERENCES dcsa_im_v3_0.carrier (id),
    carrier_service_code varchar(5),
    carrier_service_name varchar(50),
    tradelane_id varchar(8) NULL REFERENCES dcsa_im_v3_0.tradelane(id),
    universal_service_reference varchar(8) NULL
);
 */
