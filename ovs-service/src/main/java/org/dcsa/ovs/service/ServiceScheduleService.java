package org.dcsa.ovs.service;

import lombok.RequiredArgsConstructor;
import org.dcsa.ovs.persistence.repository.ServiceScheduleRepository;
import org.dcsa.ovs.mapping.ListMapper;
import org.dcsa.ovs.mapping.ServiceScheduleMapper;
import org.dcsa.ovs.transferobjects.ServiceScheduleTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ServiceScheduleService {
  private final ServiceScheduleRepository repository;
  private final ServiceScheduleMapper mapper;

  public List<ServiceScheduleTO> findAll() {
    return ListMapper.convertList(repository.findAll(), mapper::toTO);
  }
}
