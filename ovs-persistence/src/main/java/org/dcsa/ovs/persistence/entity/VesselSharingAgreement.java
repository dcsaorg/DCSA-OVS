package org.dcsa.ovs.persistence.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "vessel_sharing_agreement")
public class VesselSharingAgreement {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "vessel_sharing_agreement_name", length = 50)
  private String name;

  @Column(name = "vessel_sharing_agreement_type_code", length = 50)
  private String typeCode;
}
/*
CREATE TABLE dcsa_im_v3_0.vessel_sharing_agreement (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    vessel_sharing_agreement_name varchar(50) NULL,
    vessel_sharing_agreement_type_code varchar(3) NOT NULL REFERENCES dcsa_im_v3_0.vessel_sharing_agreement_type(vessel_sharing_agreement_type_code)
);
 */
