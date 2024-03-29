package org.dcsa.ovs.persistence.repository;

import lombok.NonNull;
import org.dcsa.ovs.persistence.entity.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ServiceRepository
    extends JpaRepository<Service, UUID>, JpaSpecificationExecutor<Service> {
  @Override
  @EntityGraph(value = "graph.vessels")
  Page<Service> findAll(Specification<Service> spec, @NonNull Pageable pageable);
}
