package org.dcsa.ovs.mapping;

import org.dcsa.ovs.transferobjects.AddressTO;
import org.dcsa.skernel.domain.persistence.entity.Address;
import org.dcsa.skernel.test.helpers.FieldValidator;
import org.junit.jupiter.api.Test;

public class AddressMapperTest {
  @Test
  public void testFieldsAreEqual() {
    FieldValidator.assertFieldsAreEqual(Address.class, AddressTO.class, "id");
  }
}
