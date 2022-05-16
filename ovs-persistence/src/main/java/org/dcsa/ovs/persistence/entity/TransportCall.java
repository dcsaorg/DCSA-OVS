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
@Table(name = "transport_call")
public class TransportCall {
  @Id
  @GeneratedValue
  @Column(name = "id", nullable = false)
  private UUID id;

  @Column(name = "transport_call_reference", length = 100, nullable = false)
  private String reference; // Really a UUID

  @Column(name = "transport_call_sequence_number")
  private Integer sequenceNumber;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "facility_id")
  private Facility facility;

  @Column(name = "facility_type_code", length = 4)
  private String facilityTypeCode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "location_id")
  private Location location;

  @Column(name = "mode_of_transport_code", length = 3)
  private String modeOfTransportCode;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "vessel_id")
  private Vessel vessel;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "import_voyage_id")
  private Voyage importVoyage;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "export_voyage_id")
  private Voyage exportVoyage;
}
/*
CREATE TABLE dcsa_im_v3_0.transport_call (
    id uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    transport_call_reference varchar(100) NOT NULL DEFAULT uuid_generate_v4(),
    transport_call_sequence_number integer,
    facility_id uuid NULL REFERENCES dcsa_im_v3_0.facility (id),
    facility_type_code char(4) NULL REFERENCES dcsa_im_v3_0.facility_type (facility_type_code),
    other_facility varchar(50) NULL, -- Free text field used if the facility cannot be identified
    location_id varchar(100) NULL REFERENCES dcsa_im_v3_0.location (id),
    mode_of_transport_code varchar(3) NULL REFERENCES dcsa_im_v3_0.mode_of_transport (mode_of_transport_code),
    vessel_id uuid NULL REFERENCES dcsa_im_v3_0.vessel(id),
    import_voyage_id uuid NULL, -- Will add the reference later once Voyage is added,
    export_voyage_id uuid NULL -- Will add the reference later once Voyage is added
);
 */
