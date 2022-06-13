package org.dcsa.ovs.persistence.entity;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "vessel_schedule")
public class VesselSchedule {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "vessel_id")
  private UUID vesselId;

  @Column(name = "service_id")
  private UUID serviceId;

  @Column(name = "created_date_time", nullable = false)
  private OffsetDateTime createdDateTime;
}
