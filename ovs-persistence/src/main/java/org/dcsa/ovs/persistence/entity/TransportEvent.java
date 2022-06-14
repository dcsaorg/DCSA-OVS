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
@Table(name = "transport_event")
public class TransportEvent {

  @Id
  @GeneratedValue
  @Column(name = "event_id", nullable = false)
  private UUID id;

  @Column(name = "event_classifier_code", length = 3, nullable = false)
  private String classifierCode;

  @Column(name = "event_created_date_time", nullable = false)
  private OffsetDateTime createdDateTime;

  @Column(name = "event_date_time", nullable = false)
  private OffsetDateTime dateTime;

  @Column(name = "transport_event_type_code", length = 4, nullable = false)
  private String typeCode;

  @Column(name = "delay_reason_code", length = 4)
  private String delayReasonCode;

  @Column(name = "change_remark", length = 250)
  private String changeRemark;
}
