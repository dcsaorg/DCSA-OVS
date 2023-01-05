package org.dcsa.ovs.controller;

import lombok.RequiredArgsConstructor;
import org.dcsa.ovs.persistence.entity.Service_;
import org.dcsa.ovs.service.VesselScheduleService;
import org.dcsa.ovs.transferobjects.ServiceScheduleTO;
import org.dcsa.skernel.infrastructure.pagination.Cursor;
import org.dcsa.skernel.infrastructure.pagination.CursorDefaults;
import org.dcsa.skernel.infrastructure.pagination.PagedResult;
import org.dcsa.skernel.infrastructure.pagination.Paginator;
import org.dcsa.skernel.infrastructure.validation.ValidVesselIMONumber;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.Size;
import java.util.List;

@Validated
@RestController
@RequiredArgsConstructor
public class ServiceScheduleController {

  private final VesselScheduleService vesselScheduleService;
  private final Paginator paginator;

  @GetMapping(path = "/service-schedules")
  @ResponseStatus(HttpStatus.OK)
  public List<ServiceScheduleTO> findAll(
    @Size(max = 5) @RequestParam(required = false) String carrierServiceCode,
    @Size(max = 8) @RequestParam(required = false) String universalServiceReference,
    @ValidVesselIMONumber(allowNull = true) @RequestParam(required = false) String vesselIMONumber,
    @Size(max = 35) @RequestParam(required = false) String vesselName,
    @Size(max = 50) @RequestParam(required = false) String voyageNumber,
    @RequestParam(required = false) String universalVoyageReference,
    @Size(max = 5) @RequestParam(value = "UNLocationCode", required = false)
          String unLocationCode,
    @Size(max = 5) @RequestParam(required = false) String facilitySMDGCode,
    @RequestParam(required = false) String startDate,
    @RequestParam(required = false) String endDate,
    @RequestParam(required = false, defaultValue = "100") Integer limit,
    @RequestParam(value = "API-Version", required = false) String apiVersion,
    HttpServletRequest request, HttpServletResponse response) {

    Cursor cursor = paginator.parseRequest(
        request,
        new CursorDefaults(limit, Sort.Direction.DESC, Service_.CARRIER_SERVICE_CODE));

    PagedResult<ServiceScheduleTO> result = vesselScheduleService.findAll(
      cursor,
      VesselScheduleService.ServiceSchedulesFilters.builder()
        .carrierServiceCode(carrierServiceCode)
        .universalServiceReference(universalServiceReference)
        .vesselIMONumber(vesselIMONumber)
        .vesselName(vesselName)
        .voyageNumber(voyageNumber)
        .universalVoyageReference(universalVoyageReference)
        .unLocationCode(unLocationCode)
        .facilitySMDGCode(facilitySMDGCode)
        .startDate(startDate)
        .endDate(endDate)
        .limit(limit)
        .build());

    paginator.setPageHeaders(request, response, cursor, result);
    return result.content();
  }
}
