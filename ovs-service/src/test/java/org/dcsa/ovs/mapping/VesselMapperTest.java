package org.dcsa.ovs.mapping;

import org.dcsa.ovs.persistence.entity.Vessel;
import org.dcsa.ovs.transferobjects.VesselTO;
import org.dcsa.skernel.test.helpers.FieldValidator;
import org.junit.jupiter.api.Test;

public class VesselMapperTest {
  @Test
  public void testFieldsAreEqual() {
    FieldValidator.assertFieldsAreEqual(Vessel.class, VesselTO.class, "id",
      // Special mapping
      "vesselOperatorCarrierSMDGCode",
      // Unmapped
      "vesselOperatorCarrier", "vesselFlag"
    );
  }
}
