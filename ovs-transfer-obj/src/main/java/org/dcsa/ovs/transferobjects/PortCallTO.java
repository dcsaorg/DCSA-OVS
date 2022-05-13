package org.dcsa.ovs.transferobjects;

import lombok.Builder;
import org.dcsa.ovs.transferobjects.enums.PortCallStatusCode;

import java.util.List;

@Builder
public record PortCallTO(
  String transportCallReference,
  String importVoyageNumber,
  String exportVoyageNumber,
  String importUniversalVoyageReference,
  String exportUniversalVoyageReference,
  PortTerminalLocation portTerminalLocation,
  PortCallStatusCode portCallStatusCode,
  List<TimestampTO> timestamps
) {
}
