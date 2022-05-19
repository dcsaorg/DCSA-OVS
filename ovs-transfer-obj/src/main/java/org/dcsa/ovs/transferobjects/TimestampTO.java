package org.dcsa.ovs.transferobjects;

import lombok.Builder;

import java.time.OffsetDateTime;

public record TimestampTO(
  String eventTypeCode,
  String eventClassifierCode,
  OffsetDateTime eventDateTime,
  String delayReasonCode,
  String changeRemark
) {
  @Builder // workaround for intellij issue
  public TimestampTO {}
}
