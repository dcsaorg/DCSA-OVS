package org.dcsa.ovs.service;

import lombok.RequiredArgsConstructor;
import org.dcsa.ovs.db.entity.ServiceSchedule;
import org.dcsa.ovs.db.repository.ServiceScheduleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceScheduleService {
  private final ServiceScheduleRepository repository;

  public List<ServiceSchedule> findAll() {
    return repository.findAll();
  }
}
