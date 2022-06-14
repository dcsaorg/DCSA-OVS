package org.dcsa.ovs.persistence.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "vessel_schedule_terminal_visits")
public class VesselScheduleTerminalVisits {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id; // Only to satisfy JPA/Hibernate

  @JoinColumn(name = "vessel_schedule_id")
  private UUID vesselScheduleId;

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
