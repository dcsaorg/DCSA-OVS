package org.dcsa.ovs.persistence.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "actual_arrival_event_id")
  private TransportEvent actualArrival;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "planned_arrival_event_id", nullable = false)
  private TransportEvent plannedArrival;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "estimated_arrival_event_id")
  private TransportEvent estimatedArrival;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "actual_departure_event_id")
  private TransportEvent actualDeparture;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "planned_departure_event_id", nullable = false)
  private TransportEvent plannedDeparture;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "estimated_departure_event_id")
  private TransportEvent estimatedDeparture;

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "port_call_status_event_id")
  private TransportEvent portCallStatus;

  @Column(name = "transport_call_sequence", nullable = false)
  private Integer transportCallSequence;

  @Column(name = "created_date_time", nullable = false)
  private OffsetDateTime createdDateTime;
}
