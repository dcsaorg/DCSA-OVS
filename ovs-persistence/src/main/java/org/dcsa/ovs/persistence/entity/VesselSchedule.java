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
  private UUID vessel;

  @Column(name = "service_id")
  private UUID service;

  @Column(name = "created_date_time", nullable = false)
  private OffsetDateTime createdDateTime;
}
/*
CREATE TABLE dcsa_im_v3_0.vessel_schedule (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    vessel_id uuid NOT NULL REFERENCES dcsa_im_v3_0.vessel (id),
    service_id uuid NOT NULL REFERENCES dcsa_im_v3_0.service (id),
    created_date_time timestamp with time zone NOT NULL DEFAULT now()
);
 */
