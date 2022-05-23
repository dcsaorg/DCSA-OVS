package org.dcsa.ovs.transferobjects;

import lombok.Builder;

public record FacilitySMDGLocationTO(
  String locationName,
  String UNLocationCode,
  String facilitySMDGCode
) implements PortTerminalLocation {
  @Builder // workaround for intellij issue
  public FacilitySMDGLocationTO { }
}
