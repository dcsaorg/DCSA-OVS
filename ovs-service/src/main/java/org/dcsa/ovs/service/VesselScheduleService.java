package org.dcsa.ovs.service;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.dcsa.ovs.mapping.ServiceScheduleMapper;
import org.dcsa.ovs.persistence.entity.Service;
import org.dcsa.ovs.persistence.repository.ServiceRepository;
import org.dcsa.ovs.persistence.repository.specification.ServiceSpecification;
import org.dcsa.ovs.transferobjects.ServiceScheduleTO;
import org.dcsa.skernel.infrastructure.pagination.Cursor;
import org.dcsa.skernel.infrastructure.pagination.PagedResult;

import java.util.function.Function;
import java.util.stream.Collectors;

import static org.dcsa.ovs.persistence.repository.specification.ServiceSpecification.withFilters;

@org.springframework.stereotype.Service
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

    var specification = withFilters(
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
        .build());
    var page = serviceRepository.findAll(specification, cursor.toPageRequest());

    var id2Service = serviceRepository.findAllById(page.stream().map(Service::getId).toList())
      .stream()
      .collect(Collectors.toMap(Service::getId, Function.identity()));
    return new PagedResult<>(page
        .map(s -> id2Service.get(s.getId()))
        .map(serviceScheduleMapper::toTO));

  }
}
