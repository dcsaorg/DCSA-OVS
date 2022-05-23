package org.dcsa.ovs.mapping;

import org.dcsa.ovs.persistence.entity.Address;
import org.dcsa.ovs.transferobjects.AddressTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
  AddressTO toTO(Address address);
}
