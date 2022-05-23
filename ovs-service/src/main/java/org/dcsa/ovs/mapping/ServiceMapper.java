package org.dcsa.ovs.mapping;

import org.dcsa.ovs.persistence.entity.Service;
import org.dcsa.ovs.transferobjects.VesselScheduleTO;
import org.mapstruct.Mapper;

@Mapper(
    componentModel = "spring",
    uses = {VesselMapper.class})
public interface ServiceMapper {
  VesselScheduleTO toTO(Service service);
}
