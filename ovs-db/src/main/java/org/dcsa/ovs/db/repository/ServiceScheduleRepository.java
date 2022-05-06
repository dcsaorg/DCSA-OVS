package org.dcsa.ovs.db.repository;

import org.dcsa.ovs.db.entity.ServiceSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceScheduleRepository extends JpaRepository<ServiceSchedule, UUID> {
}
