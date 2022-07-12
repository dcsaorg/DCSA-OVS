package org.dcsa.ovs.mapping;

import org.dcsa.ovs.persistence.entity.Service;
import org.dcsa.ovs.transferobjects.ServiceScheduleTO;
import org.mapstruct.Mapper;

@Mapper(
  componentModel = "spring",
  uses = {VesselScheduleMapper.class})
public interface ServiceScheduleMapper {
  ServiceScheduleTO toTO(Service service);
}
