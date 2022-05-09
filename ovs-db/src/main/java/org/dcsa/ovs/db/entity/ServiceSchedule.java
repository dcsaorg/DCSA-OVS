package org.dcsa.ovs.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Entity
@Table(name = "service_schedule")
public class ServiceSchedule {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;
}
