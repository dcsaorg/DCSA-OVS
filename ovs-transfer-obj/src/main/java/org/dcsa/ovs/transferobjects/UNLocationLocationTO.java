package org.dcsa.ovs.transferobjects;

import lombok.Builder;

@Builder
public record UNLocationLocationTO(
  String locationName,
  String UNLocationCode
) implements PortTerminalLocation { }
