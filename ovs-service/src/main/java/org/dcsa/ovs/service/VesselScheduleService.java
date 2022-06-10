package org.dcsa.ovs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.dcsa.ovs.mapping.ServiceMapper;
import org.dcsa.ovs.persistence.entity.Service_;
import org.dcsa.ovs.persistence.repository.ServiceRepository;
import org.dcsa.ovs.persistence.repository.specification.ServiceSpecification;
import org.dcsa.ovs.transferobjects.VesselScheduleTO;
import org.dcsa.skernel.infrastructure.pagination.Cursor;
import org.dcsa.skernel.infrastructure.pagination.CursorDefaults;
import org.dcsa.skernel.infrastructure.pagination.Paginator;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.dcsa.ovs.persistence.repository.specification.ServiceSpecification.withFilters;

@Service
@RequiredArgsConstructor
public class VesselScheduleService {

  private final ServiceRepository serviceRepository;

  private final ServiceMapper serviceMapper;

  private final ObjectMapper objectMapper;

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

  public List<VesselScheduleTO> findAll(
      HttpServletRequest request, final ServiceSchedulesFilters requestFilters) {

    Paginator paginator = new Paginator(objectMapper);
    Cursor c =
        paginator.parseRequest(
            request,
            new CursorDefaults(
                requestFilters.limit,
                new Cursor.SortBy(Sort.Direction.DESC, Service_.CARRIER_SERVICE_CODE)));

    return serviceRepository
        .findAll(
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
            c.toPageRequest())
        .stream()
        .map(serviceMapper::toTO)
        .toList();
  }
}
