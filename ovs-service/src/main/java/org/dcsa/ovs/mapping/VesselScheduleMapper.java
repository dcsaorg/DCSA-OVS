package org.dcsa.ovs.mapping;

import org.dcsa.ovs.persistence.entity.VesselSchedule;
import org.dcsa.ovs.transferobjects.VesselScheduleTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VesselScheduleMapper {
  VesselScheduleTO toTO(VesselSchedule src);
}
