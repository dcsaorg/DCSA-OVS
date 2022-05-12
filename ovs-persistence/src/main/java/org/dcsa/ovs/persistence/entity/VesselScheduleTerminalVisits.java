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
@Table(name = "vessel_schedule_terminal_visits")
public class VesselScheduleTerminalVisits {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id; // Only to satisfy JPA/Hibernate

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vessel_schedule_id")
  private VesselSchedule vesselSchedule;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "actual_arrival_event_id")
  private TransportEvent actualArrival;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "planned_arrival_event_id", nullable = false)
  private TransportEvent plannedArrival;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "estimated_arrival_event_id")
  private TransportEvent estimatedArrival;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "actual_departure_event_id")
  private TransportEvent actualDeparture;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "planned_departure_event_id", nullable = false)
  private TransportEvent plannedDeparture;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "estimated_departure_event_id")
  private TransportEvent estimatedDeparture;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "port_call_status_event_id")
  private TransportEvent portCallStatus;

  @Column(name = "transport_call_sequence", nullable = false)
  private Integer transportCallSequence;

  @Column(name = "created_date_time", nullable = false)
  private OffsetDateTime createdDateTime;
}
/*
CREATE TABLE dcsa_im_v3_0.vessel_schedule_terminal_visits (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY, -- JPA/Hibernate requires an identifying field
    vessel_schedule_id uuid NOT NULL REFERENCES dcsa_im_v3_0.vessel_schedule (id),
    actual_arrival_event_id uuid NULL REFERENCES dcsa_im_v3_0.transport_event (event_id),
    planned_arrival_event_id uuid NOT NULL REFERENCES dcsa_im_v3_0.transport_event (event_id),
    estimated_arrival_event_id uuid NULL REFERENCES dcsa_im_v3_0.transport_event (event_id),
    actual_departure_event_id uuid NULL REFERENCES dcsa_im_v3_0.transport_event (event_id),
    planned_departure_event_id uuid NOT NULL REFERENCES dcsa_im_v3_0.transport_event (event_id),
    estimated_departure_event_id uuid NULL REFERENCES dcsa_im_v3_0.transport_event (event_id),
    port_call_status_event_id uuid NULL REFERENCES dcsa_im_v3_0.transport_event (event_id),
    transport_call_sequence integer NOT NULL,
    created_date_time timestamp with time zone NOT NULL DEFAULT now()
);
 */
