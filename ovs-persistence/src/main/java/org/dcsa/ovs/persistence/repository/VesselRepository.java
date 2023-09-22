package org.dcsa.ovs.persistence.repository;

import org.dcsa.ovs.persistence.entity.Service;
import org.dcsa.ovs.persistence.entity.Vessel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface VesselRepository extends JpaRepository<Vessel, UUID>, JpaSpecificationExecutor<Vessel> {}
