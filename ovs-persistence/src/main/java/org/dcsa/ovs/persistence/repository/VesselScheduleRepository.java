package org.dcsa.ovs.persistence.repository;

import org.dcsa.ovs.persistence.entity.VesselSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VesselScheduleRepository extends JpaRepository<VesselSchedule, UUID> {
}
