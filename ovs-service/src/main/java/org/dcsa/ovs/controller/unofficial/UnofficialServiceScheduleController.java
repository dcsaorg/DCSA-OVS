package org.dcsa.ovs.controller.unofficial;

import lombok.RequiredArgsConstructor;
import org.dcsa.ovs.service.unofficial.UnofficialServiceScheduleService;
import org.dcsa.ovs.transferobjects.ServiceScheduleTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequiredArgsConstructor
@RequestMapping("/unofficial")
public class UnofficialServiceScheduleController {


  private final UnofficialServiceScheduleService serviceScheduleService;
    @PostMapping(path = "/service-schedules/")
    @ResponseStatus(HttpStatus.CREATED)
    public void createEvent(@RequestBody List<ServiceScheduleTO> serviceScheduleTOS) {
      serviceScheduleTOS.forEach(serviceScheduleService::saveServiceSchedules);
    }
  }

