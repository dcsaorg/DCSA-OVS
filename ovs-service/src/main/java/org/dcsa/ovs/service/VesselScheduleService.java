package org.dcsa.ovs.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.dcsa.ovs.mapping.ServiceMapper;
import org.dcsa.ovs.persistence.repository.ServiceRepository;
import org.dcsa.ovs.transferobjects.VesselScheduleTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VesselScheduleService {

  private final ServiceRepository serviceRepository;

  private final ServiceMapper serviceMapper;

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
        .map(serviceMapper::toTO)
        .toList();
  }
}
