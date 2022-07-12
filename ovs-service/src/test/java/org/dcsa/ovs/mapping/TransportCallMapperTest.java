package org.dcsa.ovs.mapping;

import org.dcsa.ovs.persistence.entity.TransportCall;
import org.dcsa.ovs.transferobjects.TransportCallTO;
import org.dcsa.skernel.test.helpers.FieldValidator;
import org.junit.jupiter.api.Test;

public class TransportCallMapperTest {
  @Test
  public void testTargetFieldsPresentInSrc() {
    FieldValidator.assertTargetFieldsPresentInSrc(
        TransportCall.class,
        TransportCallTO.class,
        // Special mappings
        "portVisitReference",
        "location",
        "transportCall",
        "importVoyageNumber",
        "carrierImportVoyageNumber",
        "carrierExportVoyageNumber",
        "exportVoyage",
        "statusCode",
        "universalImportVoyageReference",
        "universalExportVoyageReference");
  }
}
