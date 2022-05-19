package org.dcsa.ovs.transferobjects;

import lombok.Builder;

import java.util.List;

public record VesselScheduleTO (
  String carrierServiceName,
  String carrierServiceCode,
  String universalServiceReference,
  List<VesselTO> vessels
){
  @Builder // workaround for intellij issue
  public VesselScheduleTO {}
}
