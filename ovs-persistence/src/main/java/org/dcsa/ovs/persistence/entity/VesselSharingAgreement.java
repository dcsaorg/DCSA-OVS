package org.dcsa.ovs.persistence.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
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
  private String vesselSharingAgreementName;

  @Column(name = "vessel_sharing_agreement_type_code", length = 50)
  private String vesselSharingAgreementTypeCode;
}
