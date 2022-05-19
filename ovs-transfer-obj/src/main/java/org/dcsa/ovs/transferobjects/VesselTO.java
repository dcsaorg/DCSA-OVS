package org.dcsa.ovs.transferobjects;

import lombok.Builder;
import org.dcsa.ovs.transferobjects.enums.DimensionUnit;

import java.util.List;

public record VesselTO(
  String vesselOperatorCarrierSMDGCode,
  String vesselIMONumber,
  String vesselName,
  String vesselCallSign,
  Float vesselLength,
  Float vesselWidth,
  DimensionUnit dimensionUnit,
  Boolean isDummyVessel,
  List<PortCallTO> portCalls
){
  @Builder // workaround for intellij issue
  public VesselTO {}
}
