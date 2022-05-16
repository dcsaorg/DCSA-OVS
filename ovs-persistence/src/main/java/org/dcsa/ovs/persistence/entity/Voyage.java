package org.dcsa.ovs.persistence.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "voyage")
public class Voyage {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "carrier_voyage_number", length = 50, nullable = false)
  private String carrierVoyageNumber;

  @Column(name = "universal_voyage_reference", length = 5)
  private String universalVoyageReference;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "service_id")
  private Service service;
}
/*
CREATE TABLE dcsa_im_v3_0.voyage (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    carrier_voyage_number varchar(50) NOT NULL,
    universal_voyage_reference varchar(5) NULL,
    service_id uuid NULL REFERENCES dcsa_im_v3_0.service (id) INITIALLY DEFERRED
);
 */
