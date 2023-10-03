package org.dcsa.ovs.mapping;

import org.dcsa.ovs.persistence.entity.Vessel;
import org.dcsa.ovs.transferobjects.VesselScheduleTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {TransportCallMapper.class})
public interface VesselScheduleMapper {
  @Mapping(target = "vesselOperatorSMDGLinerCode", source = "vesselOperatorCarrier.smdgCode")
  VesselScheduleTO toTo(Vessel vessel);

  @Mapping(target = "vesselOperatorCarrier.smdgCode", source = "vesselOperatorSMDGLinerCode")
  Vessel toEntity(VesselScheduleTO vesselScheduleTO);
}
