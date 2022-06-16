package org.dcsa.ovs.mapping;

import org.dcsa.ovs.persistence.entity.TransportCall;
import org.dcsa.ovs.transferobjects.PortCallTO;
import org.dcsa.skernel.test.helpers.FieldValidator;
import org.junit.jupiter.api.Test;

public class TransportCallMapperTest {
  @Test
  public void testTargetFieldsPresentInSrc() {
    FieldValidator.assertTargetFieldsPresentInSrc(TransportCall.class, PortCallTO.class,
      // Special mappings
      "portTerminalLocation", "transportCall", "importVoyageNumber", "importVoyage",
      "exportVoyageNumber", "exportVoyage", "importUniversalVoyageReference", "exportUniversalVoyageReference"
    );
  }
}
