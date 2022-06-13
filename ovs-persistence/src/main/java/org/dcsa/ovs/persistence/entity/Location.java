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
  @Column(name = "id", nullable = false, length = 100)
  private String id;

  @Column(name = "location_name", length = 100)
  private String name;

  @Column(name = "latitude", length = 10)
  private String latitude;

  @Column(name = "longitude", length = 11)
  private String longitude;

  @Column(name = "un_location_code", length = 5, columnDefinition = "bpchar")
  private String unLocationCode; /* REFERENCES dcsa_im_v3_0.un_location (un_location_code) */

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "address_id")
  private Address address;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "facility_id")
  private Facility facility;
}
