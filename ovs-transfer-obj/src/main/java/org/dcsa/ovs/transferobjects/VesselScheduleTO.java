package org.dcsa.ovs.transferobjects;

import lombok.Builder;

import java.util.List;

@Builder
public record VesselScheduleTO (
  String carrierServiceName,
  String carrierServiceCode,
  String universalServiceReference,
  List<VesselTO> vessels
){}
