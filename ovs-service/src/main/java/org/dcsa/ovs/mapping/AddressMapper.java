package org.dcsa.ovs.mapping;

import org.dcsa.ovs.transferobjects.AddressTO;
import org.dcsa.skernel.domain.persistence.entity.Address;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
  AddressTO toTO(Address address);
}
