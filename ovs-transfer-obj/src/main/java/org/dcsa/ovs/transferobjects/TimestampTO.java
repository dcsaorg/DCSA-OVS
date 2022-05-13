package org.dcsa.ovs.transferobjects;

import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
public record TimestampTO(
  String eventTypeCode,
  String eventClassifierCode,
  OffsetDateTime eventDateTime,
  String delayReasonCode,
  String changeRemark
) { }
