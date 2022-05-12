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
@Table(name = "transport_call")
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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "transport_call_id")
  private TransportCall transportCall;
}
/*
CREATE TABLE dcsa_im_v3_0.event (
    event_id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    event_classifier_code varchar(3) NOT NULL REFERENCES dcsa_im_v3_0.event_classifier(event_classifier_code),
    event_created_date_time timestamp with time zone DEFAULT now() NOT NULL,
    event_date_time timestamp with time zone NOT NULL
);
CREATE TABLE dcsa_im_v3_0.transport_event (
    transport_event_type_code varchar(4) NOT NULL REFERENCES dcsa_im_v3_0.transport_event_type(transport_event_type_code),
    delay_reason_code varchar(4) NULL REFERENCES dcsa_im_v3_0.smdg_delay_reason(delay_reason_code),
    change_remark varchar(250),
    transport_call_id uuid NULL REFERENCES dcsa_im_v3_0.transport_call(id)
) INHERITS (dcsa_im_v3_0.event);
 */
