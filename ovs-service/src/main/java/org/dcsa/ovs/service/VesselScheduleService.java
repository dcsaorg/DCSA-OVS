package org.dcsa.ovs.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.dcsa.ovs.mapping.ServiceScheduleMapper;
import org.dcsa.ovs.persistence.repository.ServiceRepository;
import org.dcsa.ovs.persistence.repository.specification.ServiceSpecification;
import org.dcsa.ovs.transferobjects.ServiceScheduleTO;
import org.dcsa.skernel.infrastructure.pagination.Cursor;
import org.dcsa.skernel.infrastructure.pagination.PagedResult;
import org.springframework.stereotype.Service;

import static org.dcsa.ovs.persistence.repository.specification.ServiceSpecification.withFilters;

@Service
@RequiredArgsConstructor
public class VesselScheduleService {

  private final ServiceRepository serviceRepository;

  private final ServiceScheduleMapper serviceScheduleMapper;

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

  public PagedResult<ServiceScheduleTO> findAll(
      Cursor cursor, final ServiceSchedulesFilters requestFilters) {

    return new PagedResult<>(
        serviceRepository.findAll(
            withFilters(
                ServiceSpecification.ServiceSchedulesFilters.builder()
                    .carrierServiceCode(requestFilters.carrierServiceCode)
                    .universalServiceReference(requestFilters.universalServiceReference)
                    .vesselIMONumber(requestFilters.vesselIMONumber)
                    .vesselName(requestFilters.vesselName)
                    .voyageNumber(requestFilters.voyageNumber)
                    .universalVoyageReference(requestFilters.universalVoyageReference)
                    .unLocationCode(requestFilters.unLocationCode)
                    .facilitySMDGCode(requestFilters.facilitySMDGCode)
                    .startDate(requestFilters.startDate)
                    .endDate(requestFilters.endDate)
                    .build()),
            cursor.toPageRequest()),
        serviceScheduleMapper::toTO);
  }
}
