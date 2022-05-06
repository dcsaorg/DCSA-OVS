package org.dcsa.ovs.controller;

import lombok.RequiredArgsConstructor;
import org.dcsa.ovs.db.entity.ServiceSchedule;
import org.dcsa.ovs.service.ServiceScheduleService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class OvsRestController {
  private final ServiceScheduleService service;

  @GetMapping(path = "/")
  public List<ServiceSchedule> findAll() {
    return service.findAll();
  }
}
