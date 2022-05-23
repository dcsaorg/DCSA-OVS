package org.dcsa.ovs.mapping;

import org.dcsa.ovs.persistence.entity.TransportCall;
import org.dcsa.ovs.transferobjects.PortCallTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PortCallMapper {
  @Mappings(
      value = {
        @Mapping(target = "timestamps", ignore = true),
        @Mapping(target = "portTerminalLocation", ignore = true),
        @Mapping(target = "transportCallReference", source = "reference"),
        @Mapping(target = "importVoyageNumber", source = "importVoyage.carrierVoyageNumber"),
        @Mapping(target = "exportVoyageNumber", source = "exportVoyage.carrierVoyageNumber"),
        @Mapping(
            target = "importUniversalVoyageReference",
            source = "importVoyage.universalVoyageReference"),
        @Mapping(
            target = "exportUniversalVoyageReference",
            source = "exportVoyage.universalVoyageReference")
      })
  PortCallTO toTO(TransportCall transportCall);
}
