package org.dcsa.ovs.transferobjects;

import lombok.Builder;

@Builder
public record FacilitySMDGLocationTO(
  String locationName,
  String UNLocationCode,
  String facilitySMDGCode
) implements PortTerminalLocation { }
