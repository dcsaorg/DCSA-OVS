package org.dcsa.ovs.mapping;

import org.dcsa.ovs.persistence.entity.ServiceSchedule;
import org.dcsa.ovs.transferobjects.ServiceScheduleTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ServiceScheduleMapper {
  ServiceScheduleTO toTO(ServiceSchedule src);
}
