package org.dcsa.ovs.persistence.repository;

import org.dcsa.ovs.persistence.entity.Service;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ServiceRepository
    extends JpaRepository<Service, UUID>, JpaSpecificationExecutor<Service> {
  @Override
  @EntityGraph(value = "graph.vessels")
  List<Service> findAll(Specification<Service> spec);
}
