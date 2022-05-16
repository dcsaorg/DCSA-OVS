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
@Table(name = "address")
public class Address {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "name", length = 100)
  private String name;

  @Column(name = "street", length = 100)
  private String street;

  @Column(name = "street_number", length = 50)
  private String streetNumber;

  @Column(name = "floor", length = 50)
  private String floor;

  @Column(name = "postal_code", length = 10)
  private String postalCode;

  @Column(name = "city", length = 65)
  private String city;

  @Column(name = "state_region", length = 65)
  private String stateRegion;

  @Column(name = "country", length = 65)
  private String country;
}
/*
CREATE TABLE dcsa_im_v3_0.address (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    name varchar(100) NULL,
    street varchar(100) NULL,
    street_number varchar(50) NULL,
    floor varchar(50) NULL,
    postal_code varchar(10) NULL,
    city varchar(65) NULL,
    state_region varchar(65) NULL,
    country varchar(75) NULL
);
 */
