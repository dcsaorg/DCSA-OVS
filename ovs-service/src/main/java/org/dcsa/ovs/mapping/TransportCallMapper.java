package org.dcsa.ovs.mapping;

import org.dcsa.ovs.persistence.entity.Address;
import org.dcsa.ovs.persistence.entity.Facility;
import org.dcsa.ovs.persistence.entity.Location;
import org.dcsa.ovs.persistence.entity.TransportCall;
import org.dcsa.ovs.transferobjects.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Objects;

@Mapper(
    componentModel = "spring",
    uses = {TransportEventMapper.class})
public abstract class TransportCallMapper {
  @Mappings(
      value = {
        @Mapping(target = "portTerminalLocation", source = "transportCall"),
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
  public abstract PortCallTO toTO(TransportCall transportCall);

  // decide return data based on nullable fields
  PortTerminalLocation mapPortTerminalLocation(TransportCall transportCall) {
    // default rule : if both facility and address are present on location we return
    // PortTerminalLocation: facilitySMDGLocation
    PortTerminalLocation portTerminalLocation = null;
    Location location = transportCall.getLocation();

    // since the spring context cannot be accessed here we access the mapper via code
    AddressMapper addressMapper = Mappers.getMapper(AddressMapper.class);

    if (null != location) {

      if (null != location.getFacility()) {

        portTerminalLocation =
            FacilitySMDGLocationTO.builder()
                .locationName(location.getName())
                .UNLocationCode(location.getUnLocationCode())
                .facilitySMDGCode(
                    Objects.requireNonNullElseGet(location.getFacility(), Facility::new)
                        .getSmdgCode())
                .build();

      } else if (null != location.getAddress()) {

        Address address = location.getAddress();
        AddressTO addressTO = addressMapper.toTO(address);

        portTerminalLocation =
            AddressLocationTO.builder().locationName(location.getName()).address(addressTO).build();

      } else {

        portTerminalLocation =
            UNLocationLocationTO.builder()
                .locationName(location.getName())
                .UNLocationCode(location.getUnLocationCode())
                .build();
      }
    }

    return portTerminalLocation;
  }
}
