package org.dcsa.ovs.service;

import lombok.RequiredArgsConstructor;
import org.dcsa.ovs.db.entity.ServiceSchedule;
import org.dcsa.ovs.db.repository.ServiceScheduleRepository;
import org.dcsa.ovs.mapping.ListMapper;
import org.dcsa.ovs.mapping.ServiceScheduleMapper;
import org.dcsa.ovs.transferobjects.ServiceScheduleTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ServiceScheduleService {
  private final ServiceScheduleRepository repository;
  private final ServiceScheduleMapper mapper;

  public List<ServiceScheduleTO> findAll() {
    return ListMapper.convertList(repository.findAll(), mapper::toTO);
  }
}
