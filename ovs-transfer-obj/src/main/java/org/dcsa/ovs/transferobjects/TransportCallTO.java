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
  //The possible values for the Port call status code can be found in https://github.com/dcsaorg/DCSA-Information-Model/blob/master/datamodel/referencedata.d/portcallstatuscodes.csv
  String statusCode,
  List<TimestampTO> timestamps
) {
  @Builder(toBuilder = true) // workaround for intellij issue
  public TransportCallTO {}
}
