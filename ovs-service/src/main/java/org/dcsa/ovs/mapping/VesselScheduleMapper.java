package org.dcsa.ovs.mapping;

import org.dcsa.ovs.persistence.entity.Service;
import org.dcsa.ovs.transferobjects.VesselScheduleTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VesselScheduleMapper {
  @Mapping(target = "vessels", ignore = true)
  VesselScheduleTO toTO(Service service);
}
