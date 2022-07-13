package org.dcsa.ovs.mapping;

import org.dcsa.ovs.persistence.entity.Service;
import org.dcsa.ovs.transferobjects.ServiceScheduleTO;
import org.dcsa.skernel.test.helpers.FieldValidator;
import org.junit.jupiter.api.Test;

class ServiceScheduleMapperTest {
  @Test
  void testFieldsAreEqual() {
    FieldValidator.assertFieldsAreEqual(
        Service.class,
        ServiceScheduleTO.class,
        "id",
        "carrier",
        "tradelane",
        "vessels",
        "vesselSchedules");
  }
}
