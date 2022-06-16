package org.dcsa.ovs.controller;

import lombok.RequiredArgsConstructor;
import org.dcsa.ovs.persistence.entity.Service_;
import org.dcsa.ovs.service.VesselScheduleService;
import org.dcsa.ovs.transferobjects.VesselScheduleTO;
import org.dcsa.skernel.infrastructure.pagination.Cursor;
import org.dcsa.skernel.infrastructure.pagination.CursorDefaults;
import org.dcsa.skernel.infrastructure.pagination.PagedResult;
import org.dcsa.skernel.infrastructure.pagination.Paginator;
import org.dcsa.skernel.infrastructure.validation.ValidVesselIMONumber;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Validated
@RestController
@RequiredArgsConstructor
public class ServiceScheduleController {

  private final VesselScheduleService vesselScheduleService;
  private final Paginator paginator;

  @GetMapping(path = "/service-schedules")
  @ResponseStatus(HttpStatus.OK)
  public List<VesselScheduleTO> findAll(
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

    PagedResult<VesselScheduleTO> result = vesselScheduleService.findAll(
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

  // TODO: Move to a handler
  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<RequestFailureTO> badRequest(
      HttpServletRequest httpServletRequest, ConstraintViolationException cvex) {

    String exceptionMessage = null;

    if (null != cvex.getConstraintViolations()) {
      exceptionMessage =
          cvex.getConstraintViolations().stream()
              .filter(Objects::nonNull)
              .map(
                  constraintViolation ->
                      constraintViolation.getPropertyPath()
                          + " "
                          + constraintViolation.getMessage())
              .collect(Collectors.joining(";"));
    }

    return new ResponseEntity<>(
        new RequestFailureTO(
            httpServletRequest.getMethod(),
            httpServletRequest.getRequestURI(),
            List.of(new ConcreteRequestErrorMessageTO("invalidInput", exceptionMessage))),
        HttpStatus.BAD_REQUEST);
  }

  record RequestFailureTO(
      String httpMethod, String requestUri, List<ConcreteRequestErrorMessageTO> errors) {}

  record ConcreteRequestErrorMessageTO(String reason, String message) {}
}
