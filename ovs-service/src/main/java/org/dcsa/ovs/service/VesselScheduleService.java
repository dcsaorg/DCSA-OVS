package org.dcsa.ovs.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.dcsa.ovs.persistence.entity.*;
import org.dcsa.ovs.persistence.repository.ServiceRepository;
import org.dcsa.ovs.transferobjects.*;
import org.dcsa.ovs.transferobjects.enums.DimensionUnit;
import org.dcsa.ovs.transferobjects.enums.PortCallStatusCode;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class VesselScheduleService {

  private final ServiceRepository serviceRepository;

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
                VesselScheduleTO.builder()
                    .carrierServiceName(service.getCarrierServiceName())
                    .carrierServiceCode(service.getCarrierServiceCode())
                    .universalServiceReference(service.getUniversalServiceReference())
                    .vessels(extractVesselsToVesselTO(service.getVessels()))
                    .build())
        .toList();
  }

  private List<VesselTO> extractVesselsToVesselTO(List<Vessel> vessels) {
    return vessels.stream()
        .map(
            vessel ->
                VesselTO.builder()
                    .vesselName(vessel.getName())
                    .vesselIMONumber(vessel.getImoNumber())
                    .vesselOperatorCarrierSMDGCode(vessel.getOperatorCarrier().getSmdgCode())
                    .vesselCallSign(vessel.getCallSign())
                    .vesselLength(vessel.getLength())
                    .vesselWidth(vessel.getWidth())
                    .dimensionUnit(
                        DimensionUnit.valueFromString(vessel.getDimensionUnit()).orElse(null))
                    .isDummyVessel(vessel.getIsDummy())
                    .portCalls(extractTransportCallToPortCallTo(vessel.getPortCalls()))
                    .build())
        .toList();
  }

  private List<PortCallTO> extractTransportCallToPortCallTo(List<TransportCall> transportCalls) {
    return transportCalls.stream()
        .map(
            transportCall ->
                PortCallTO.builder()
                    .transportCallReference(transportCall.getReference())
                    .importVoyageNumber(
                        Objects.requireNonNullElse(transportCall.getImportVoyage(), new Voyage())
                            .getCarrierVoyageNumber())
                    .exportVoyageNumber(
                        Objects.requireNonNullElse(transportCall.getExportVoyage(), new Voyage())
                            .getCarrierVoyageNumber())
                    .importUniversalVoyageReference(
                        Objects.requireNonNullElse(transportCall.getImportVoyage(), new Voyage())
                            .getUniversalVoyageReference())
                    .exportUniversalVoyageReference(
                        Objects.requireNonNullElse(transportCall.getExportVoyage(), new Voyage())
                            .getUniversalVoyageReference())
                    .portTerminalLocation(randomPortTerminalLocation(transportCall))
                    .portCallStatusCode(
                        PortCallStatusCode.valueFromString(transportCall.getPortCallStatusCode())
                            .orElse(null))
                    .timestamps(extractTransportEventToTimestampTO(transportCall.getTimestamps()))
                    .build())
        .toList();
  }

  private PortTerminalLocation randomPortTerminalLocation(TransportCall transportCall) {

    int randomInt = (int) Math.floor(Math.random() * (100) + 1);

    if (randomInt < 33) {
      // location is nullable hence a null check is required
      if (null != transportCall.getLocation()) {
        return UNLocationLocationTO.builder()
            .locationName(transportCall.getLocation().getName())
            .UNLocationCode(transportCall.getLocation().getUnLocationCode())
            .build();
      } else {
        return UNLocationLocationTO.builder().build();
      }
    } else if (randomInt >= 33 && randomInt <= 66) {
      // location is nullable hence a null check is required
      if (null != transportCall.getLocation()) {
        return FacilitySMDGLocationTO.builder()
            .locationName(transportCall.getLocation().getName())
            .UNLocationCode(transportCall.getLocation().getUnLocationCode())
            .facilitySMDGCode(
                Objects.requireNonNullElse(transportCall.getFacility(), new Facility())
                    .getSmdgCode())
            .build();
      } else {
        return FacilitySMDGLocationTO.builder().build();
      }
    } else {
      // location is nullable hence a null check is required
      if (null != transportCall.getLocation()) {

        Address address = transportCall.getLocation().getAddress();
        AddressTO addressTO;
        // address is nullable hence a null check is required
        if (null != address) {
          addressTO =
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
        } else {
          addressTO = AddressTO.builder().build();
        }

        return AddressLocationTO.builder()
            .locationName(transportCall.getLocation().getName())
            .address(addressTO)
            .build();
      } else {
        return AddressLocationTO.builder().build();
      }
    }
  }

  private List<TimestampTO> extractTransportEventToTimestampTO(
      List<TransportEvent> transportEvents) {
    return transportEvents.stream()
        .map(
            transportEvent ->
                TimestampTO.builder()
                    .eventTypeCode(transportEvent.getTypeCode())
                    .eventClassifierCode(transportEvent.getClassifierCode())
                    .eventDateTime(transportEvent.getDateTime())
                    .delayReasonCode(transportEvent.getDelayReasonCode())
                    .changeRemark(transportEvent.getChangeRemark())
                    .build())
        .toList();
  }
}
