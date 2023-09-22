package org.dcsa.ovs.service.unofficial;

import lombok.RequiredArgsConstructor;
import org.dcsa.ovs.mapping.ServiceScheduleMapper;
import org.dcsa.ovs.persistence.entity.TransportCall;
import org.dcsa.ovs.persistence.entity.Vessel;
import org.dcsa.ovs.persistence.repository.ServiceRepository;
import org.dcsa.ovs.transferobjects.ServiceScheduleTO;
import org.dcsa.ovs.transferobjects.TransportCallTO;
import org.dcsa.ovs.transferobjects.VesselScheduleTO;
import org.dcsa.skernel.domain.persistence.entity.Carrier;
import org.dcsa.skernel.domain.persistence.repository.CarrierRepository;
import org.dcsa.skernel.errors.exceptions.ConcreteRequestErrorMessageException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UnofficialServiceScheduleService {

  private final ServiceRepository serviceRepository;
  private final ServiceScheduleMapper serviceScheduleMapper;
  private final CarrierRepository carrierRepository;
  private static List STATUS_CODE =
      Arrays.asList("OMIT", "BLNK", "ADHO", "PHOT", "PHIN", "SLID", "ROTC");

  @Transactional
  public void saveServiceSchedules(ServiceScheduleTO serviceScheduleTO) {

    validatePortCallStatusCode(serviceScheduleTO.vesselSchedules());
    Set<Vessel> vessels = new LinkedHashSet<>();
    org.dcsa.ovs.persistence.entity.Service serviceEntity = serviceScheduleMapper.toEntity(serviceScheduleTO);
    for (Vessel vessel : serviceEntity.getVessels()) {
      Carrier carrier = carrierRepository.findBySmdgCode(vessel.getVesselOperatorCarrier().getSmdgCode());
      vessel.setVesselOperatorCarrier(carrier);
      vessels.add(vessel);
      for (TransportCall transportCall : vessel.getTransportCalls()) {
        transportCall.setVessel(vessel);
      }
    }
    serviceEntity.setVessels(vessels);
    serviceRepository.save(serviceEntity);
  }

  private void validatePortCallStatusCode(List<VesselScheduleTO> vesselScheduleTOS) {
    vesselScheduleTOS.stream()
      .flatMap(v -> v.transportCalls().stream())
      .filter(t -> t.statusCode() != null && !STATUS_CODE.contains(t.statusCode()))
      .forEach(t -> {
        throw ConcreteRequestErrorMessageException.invalidInput(
          "The port call status code is invalid for transport reference : "
            + t.transportCallReference());
      });
  }
}
