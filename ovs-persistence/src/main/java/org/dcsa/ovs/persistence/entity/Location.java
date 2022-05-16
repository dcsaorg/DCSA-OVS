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

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "location")
public class Location {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false, length = 100)
  private String id; // Really a UUID

  @Column(name = "location_name", length = 100)
  private String name;

  @Column(name = "latitude", length = 10)
  private String latitude;

  @Column(name = "longitude", length = 11)
  private String longitude;

  @Column(name = "un_location_code", length = 5)
  private String unLocationCode; /* REFERENCES dcsa_im_v3_0.un_location (un_location_code) */

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "address_id")
  private Address address;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "facility_id")
  private Facility facility;
}
/*
CREATE TABLE dcsa_im_v3_0.location (
    id varchar(100) DEFAULT uuid_generate_v4()::text PRIMARY KEY,
    location_name varchar(100) NULL,
    latitude varchar(10) NULL,
    longitude varchar(11) NULL,
    un_location_code char(5) NULL REFERENCES dcsa_im_v3_0.un_location (un_location_code),
    address_id uuid NULL REFERENCES dcsa_im_v3_0.address (id),
    facility_id uuid NULL  -- REFERENCES facility (but there is a circular relation, so we add the FK later)
);
 */
