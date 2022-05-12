package org.dcsa.ovs.persistence.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "vessel_schedule")
public class VesselSchedule {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vessel_id")
  private Vessel vessel;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "service_id")
  private Service service;

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
