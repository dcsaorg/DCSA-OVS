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
@Table(name = "tradelane")
public class Tradelane {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "tradelane_name", length = 150, nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "vessel_sharing_agreement_id", nullable = false)
  private VesselSharingAgreement vesselSharingAgreement;
}
/*
CREATE TABLE dcsa_im_v3_0.tradelane (
    id varchar(8) PRIMARY KEY,
    tradelane_name varchar(150) NOT NULL,
    vessel_sharing_agreement_id uuid NOT NULL REFERENCES dcsa_im_v3_0.vessel_sharing_agreement(id)
);
 */
