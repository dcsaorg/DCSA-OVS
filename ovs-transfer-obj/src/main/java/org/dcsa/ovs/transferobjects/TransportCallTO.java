package org.dcsa.ovs.transferobjects;

import lombok.Builder;
import org.dcsa.ovs.transferobjects.enums.PortCallStatusCode;

import java.util.List;

public record TransportCallTO(
  String portVisitReference,
  String transportCallReference,
  String carrierImportVoyageNumber,
  String carrierExportVoyageNumber,
  String universalImportVoyageReference,
  String universalExportVoyageReference,
  PortTerminalLocation location,
  PortCallStatusCode statusCode,
  List<TimestampTO> timestamps
) {
  @Builder(toBuilder = true) // workaround for intellij issue
  public TransportCallTO {}
}
