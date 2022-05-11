package org.dcsa.ovs.transferobjects;

import lombok.Builder;

import java.util.List;

@Builder
public record ServiceScheduleTO (
  String carrierServiceName,
  String carrierServiceCode,
  String universalServiceReference,
  List<VesselTO> vessels
){}
