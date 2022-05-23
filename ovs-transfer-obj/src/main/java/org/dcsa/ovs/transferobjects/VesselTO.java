package org.dcsa.ovs.transferobjects;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.dcsa.ovs.transferobjects.enums.DimensionUnit;

import java.util.List;

public record VesselTO(
  @JsonProperty("vesselOperatorCarrierSMDGCode")
  String operatorCarrierSMDGCode,
  @JsonProperty("vesselIMONumber")
  String imoNumber,
  @JsonProperty("vesselName")
  String name,
  @JsonProperty("vesselCallSign")
  String callSign,
  @JsonProperty("vesselLength")
  Float length,
  @JsonProperty("vesselWidth")
  Float width,
  DimensionUnit dimensionUnit,
  @JsonProperty("isDummyVessel")
  Boolean isDummy,
  List<PortCallTO> portCalls
){
  @Builder(toBuilder = true) // workaround for intellij issue
  public VesselTO {}
}
