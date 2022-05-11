package org.dcsa.ovs.transferobjects;

import lombok.Builder;

@Builder
public record AddressLocationTO(
  String locationName,
  AddressTO address
) implements PortTerminalLocation { }
