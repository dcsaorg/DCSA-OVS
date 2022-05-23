package org.dcsa.ovs.transferobjects;

import lombok.Builder;

public record AddressLocationTO(
  String locationName,
  AddressTO address
) implements PortTerminalLocation {
  @Builder // workaround for intellij issue
  public AddressLocationTO { }
}
