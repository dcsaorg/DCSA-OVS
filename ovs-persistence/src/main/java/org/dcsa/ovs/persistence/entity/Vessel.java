package org.dcsa.ovs.persistence.entity;

import lombok.Data;

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
@Entity
@Table(name = "vessel")
public class Vessel {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "vessel_imo_number", length = 7, unique = true)
  private String imoNumber;

  @Column(name = "vessel_name", length = 35)
  private String name;

  @Column(name = "vessel_flag", length = 2)
  private String flag;

  @Column(name = "vessel_call_sign", length = 18)
  private String callSign;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vessel_operator_carrier_id")
  private Carrier operatorCarrier;

  @Column(name = "is_dummy")
  private Boolean isDummy;

  @Column(name = "length")
  private Float length;

  @Column(name = "width")
  private Float width;

  @Column(name = "dimension_unit", length = 3)
  private String dimensionUnit;
}
/*
CREATE TABLE dcsa_im_v3_0.vessel (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    vessel_imo_number varchar(7) NULL UNIQUE,
    vessel_name varchar(35) NULL,
    vessel_flag char(2) NULL,
    vessel_call_sign varchar(10) NULL,
    vessel_operator_carrier_id uuid NULL REFERENCES dcsa_im_v3_0.carrier (id),
    is_dummy boolean NOT NULL default false,
    length numeric NULL,
    width numeric NULL,
    dimension_unit varchar(3) NULL REFERENCES dcsa_im_v3_0.unit_of_measure(unit_of_measure_code) CONSTRAINT dimension_unit CHECK (dimension_unit IN ('FOT','MTR'))
);
 */
