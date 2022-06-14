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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

  public static record VesselScheduleTOPage(
    int totalPages,
    List<VesselScheduleTO> list
  ) {}

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

  public VesselScheduleTOPage findAll(Cursor cursor, final ServiceSchedulesFilters requestFilters) {

    Page<org.dcsa.ovs.persistence.entity.Service> page = serviceRepository
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
            cursor.toPageRequest());

    return new VesselScheduleTOPage(page.getTotalPages(),
        page.stream()
        .map(serviceMapper::toTO)
        .toList());
  }
}
