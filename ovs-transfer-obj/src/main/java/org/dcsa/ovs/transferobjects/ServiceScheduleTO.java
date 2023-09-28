package org.dcsa.ovs.transferobjects;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

import java.util.List;

public record ServiceScheduleTO (
  String carrierServiceName,
  String carrierServiceCode,
  String universalServiceReference,
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
  String serviceOwnerCode,
  List<VesselScheduleTO> vesselSchedules
){
  @Builder(toBuilder = true) // workaround for intellij issue
  public ServiceScheduleTO {}
}
