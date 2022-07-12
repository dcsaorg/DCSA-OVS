package org.dcsa.ovs.transferobjects;

import lombok.Builder;

import java.util.List;

public record VesselScheduleTO (
  String vesselOperatorSMDGLinerCode,
  String vesselIMONumber,
  String vesselName,
  String vesselCallSign,
  Boolean isDummyVessel,
  List<TransportCallTO> transportCalls
){
  @Builder(toBuilder = true) // workaround for intellij issue
  public VesselScheduleTO {}
}
