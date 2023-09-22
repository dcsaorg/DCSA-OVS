package org.dcsa.ovs.transferobjects;

import lombok.Builder;

import java.time.OffsetDateTime;

public record TimestampTO(
  String eventTypeCode,
  String eventClassifierCode,
  OffsetDateTime eventCreatedDateTime,
  OffsetDateTime eventDateTime,
  String delayReasonCode,
  String transportEventTypeCode,
  String changeRemark
) {
  @Builder // workaround for intellij issue
  public TimestampTO {}
}
