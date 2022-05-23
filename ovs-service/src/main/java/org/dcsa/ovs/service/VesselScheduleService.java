package org.dcsa.ovs.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.dcsa.ovs.mapping.PortCallMapper;
import org.dcsa.ovs.mapping.TimestampMapper;
import org.dcsa.ovs.mapping.VesselMapper;
import org.dcsa.ovs.mapping.VesselScheduleMapper;
import org.dcsa.ovs.persistence.entity.*;
import org.dcsa.ovs.persistence.repository.ServiceRepository;
import org.dcsa.ovs.transferobjects.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VesselScheduleService {

  private final ServiceRepository serviceRepository;

  private final VesselScheduleMapper vesselScheduleMapper;

  private final VesselMapper vesselMapper;

  private final PortCallMapper portCallMapper;

  private final TimestampMapper timestampMapper;

  @Builder
  public static class ServiceSchedulesFilters {
    String carrierServiceCode;
    String universalServiceReference;
    String vesselIMONumber;
    String vesselName;
    String voyageNumber;
    String universalVoyageReference;
    String unLocationCode;
    String facilitySMDGCode;
    String startDate;
    String endDate;
    Integer limit;
    String cursor;
    String apiVersion;
  }

  public List<VesselScheduleTO> findAll(ServiceSchedulesFilters requestFilters) {

    return serviceRepository.findAll().stream()
        .map(
            service ->
                vesselScheduleMapper.toTO(service).toBuilder()
                    .vessels(extractVesselsToVesselTO(service.getVessels()))
                    .build())
        .toList();
  }

  private List<VesselTO> extractVesselsToVesselTO(List<Vessel> vessels) {
    return vessels.stream()
        .map(
            vessel ->
                vesselMapper.toTo(vessel).toBuilder()
                    .portCalls(extractTransportCallToPortCallTo(vessel.getPortCalls()))
                    .build())
        .toList();
  }

  private List<PortCallTO> extractTransportCallToPortCallTo(List<TransportCall> transportCalls) {
    return transportCalls.stream()
        .map(
            transportCall ->
                portCallMapper.toTO(transportCall).toBuilder()
                    .portTerminalLocation(getPortTerminalLocation(transportCall))
                    .timestamps(
                        transportCall.getTimestamps().stream().map(timestampMapper::toTO).toList())
                    .build())
        .toList();
  }

  // decide returned data based on nullable fields
  private PortTerminalLocation getPortTerminalLocation(TransportCall transportCall) {
    // default rule : if both facility and address are present on location we return
    // PortTerminalLocation: facilitySMDGLocation
    PortTerminalLocation portTerminalLocation = null;
    Location location = transportCall.getLocation();

    if (null != location) {

      if (null != location.getFacility()) {

        portTerminalLocation =
            FacilitySMDGLocationTO.builder()
                .locationName(location.getName())
                .UNLocationCode(location.getUnLocationCode())
                .facilitySMDGCode(
                    Objects.requireNonNullElse(transportCall.getFacility(), new Facility())
                        .getSmdgCode())
                .build();

      } else if (null != location.getAddress()) {

        Address address = location.getAddress();
        AddressTO addressTO =
            AddressTO.builder()
                .name(address.getName())
                .street(address.getStreet())
                .streetNumber(address.getStreetNumber())
                .floor(address.getFloor())
                .postCode(address.getPostalCode())
                .city(address.getCity())
                .stateRegion(address.getStateRegion())
                .country(address.getCountry())
                .build();

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
