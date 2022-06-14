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
@Table(name = "facility")
public class Facility {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "facility_name", length = 100)
  private String name;

  @Column(name = "un_location_code", length = 5)
  private String unLocationCode;

  @Column(name = "facility_bic_code", length = 4)
  private String bicCode;

  @Column(name = "facility_smdg_code", length = 6)
  private String smdgCode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "location_id")
  private Location location;
}
