package org.dcsa.ovs.datafactories;

import lombok.experimental.UtilityClass;
import org.dcsa.ovs.transferobjects.*;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class ServiceScheduleTODataFactory {

  public List<ServiceScheduleTO> serviceScheduleTOList() {
    TimestampTO timestampTO =
        TimestampTO.builder()
            .eventTypeCode("DEPA")
            .eventClassifierCode("PLN")
            .eventDateTime(OffsetDateTime.of(2021, 12, 3, 0, 11, 0, 0, ZoneOffset.UTC))
            .delayReasonCode("WEA")
            .changeRemark("Bad weather")
            .build();
    AddressTO addressTO =
        AddressTO.builder()
            .name("Lagkagehuset")
            .street("Islands Brygge")
            .streetNumber("43")
            .floor("St")
            .postCode("2300")
            .city("København S")
            .stateRegion("N/A")
            .country("Denmark")
            .build();
    PortTerminalLocation portTerminalLocation =
        AddressLocationTO.builder()
            .locationName("Lagkagehuset Islands Brygge")
            .address(addressTO)
            .build();
    TransportCallTO transportCallTO =
        TransportCallTO.builder()
            .transportCallReference("TC-REF-08_04-B")
            .carrierImportVoyageNumber("2106W")
            .carrierExportVoyageNumber("2107E")
            .location(portTerminalLocation)
            .timestamps(List.of(timestampTO))
            .build();
    VesselScheduleTO vesselScheduleTO =
        VesselScheduleTO.builder()
            .vesselOperatorSMDGLinerCode("MSK")
            .vesselIMONumber("9321483")
            .vesselName("Emma Maersk")
            .isDummyVessel(false)
            .transportCalls(List.of(transportCallTO))
            .build();
    ServiceScheduleTO serviceScheduleTO =
        ServiceScheduleTO.builder()
            .carrierServiceName("A_carrier_service_name")
            .carrierServiceCode("A_CSC")
            .universalServiceReference("SR0001D")
            .vesselSchedules(List.of(vesselScheduleTO))
            .build();
    return Collections.singletonList(serviceScheduleTO);
  }
}
