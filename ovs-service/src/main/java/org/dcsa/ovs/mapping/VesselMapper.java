package org.dcsa.ovs.mapping;

import org.dcsa.ovs.persistence.entity.Vessel;
import org.dcsa.ovs.transferobjects.VesselTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface VesselMapper {
  @Mappings(
      value = {
        @Mapping(target = "portCalls", ignore = true),
        @Mapping(target = "operatorCarrierSMDGCode", source = "operatorCarrier.smdgCode")
      })
  VesselTO toTo(Vessel vessel);
}
