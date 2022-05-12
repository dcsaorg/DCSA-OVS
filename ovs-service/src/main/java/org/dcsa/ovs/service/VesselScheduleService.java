package org.dcsa.ovs.service;

import lombok.RequiredArgsConstructor;
import org.dcsa.ovs.mapping.VesselScheduleMapper;
import org.dcsa.ovs.persistence.repository.VesselScheduleRepository;
import org.dcsa.ovs.mapping.ListMapper;
import org.dcsa.ovs.transferobjects.VesselScheduleTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VesselScheduleService {
  private final VesselScheduleRepository repository;
  private final VesselScheduleMapper mapper;

  public List<VesselScheduleTO> findAll() {
    return ListMapper.convertList(repository.findAll(), mapper::toTO);
  }
}
