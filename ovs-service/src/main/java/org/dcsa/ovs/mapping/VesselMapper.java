package org.dcsa.ovs.mapping;

import org.dcsa.ovs.persistence.entity.Vessel;
import org.dcsa.ovs.transferobjects.VesselTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
    componentModel = "spring",
    uses = {TransportCallMapper.class})
public interface VesselMapper {
  @Mapping(target = "operatorCarrierSMDGCode", source = "operatorCarrier.smdgCode")
  VesselTO toTo(Vessel vessel);
}
