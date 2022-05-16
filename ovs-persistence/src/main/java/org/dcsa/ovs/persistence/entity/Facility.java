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
@Table(name = "carrier")
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
/*
CREATE TABLE dcsa_im_v3_0.facility (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    facility_name varchar(100) NULL,
    un_location_code varchar(5) NULL REFERENCES dcsa_im_v3_0.un_location (un_location_code), -- The UN Locode prefixing the BIC / SMDG code
    facility_bic_code varchar(4) NULL, -- suffix uniquely identifying the facility when prefixed with the UN Locode
    facility_smdg_code varchar(6) NULL, -- suffix uniquely identifying the facility when prefixed with the UN Locode
    location_id varchar(100) REFERENCES dcsa_im_v3_0.location(id)
);
 */
