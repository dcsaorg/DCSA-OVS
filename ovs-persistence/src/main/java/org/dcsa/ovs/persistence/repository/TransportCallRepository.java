package org.dcsa.ovs.persistence.repository;

import org.dcsa.ovs.persistence.entity.TransportCall;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransportCallRepository extends JpaRepository<TransportCall, UUID>, JpaSpecificationExecutor<TransportCall>{}
