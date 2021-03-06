package org.dcsa.ovs.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dcsa.core.model.AuditBase;
import org.dcsa.core.model.GetId;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.UUID;

@Table("schedule")
@Data
@NoArgsConstructor
public class Schedule extends AuditBase implements GetId<UUID> {

    @Id
    @JsonProperty("scheduleID")
    private UUID id;

    @JsonProperty("")
    @Column("vessel_operator_carrier_code")
    private String vesselOperatorCarrierCode;

    @JsonProperty("")
    @Column("vessel_operator_carrier_code_list_provider")
    private String vesselOperatorCarrierCodeListProvider;

    @JsonProperty("")
    @Column("vessel_partner_carrier_code")
    private String vesselPartnerCarrierCode;

    @JsonProperty("")
    @Column("vessel_partner_carrier_code_list_provider")
    private String vesselPartnerCarrierCodeListProvider;

    @Temporal(TemporalType.DATE)
    @Column("start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Pattern(regexp = "^(P(\\d+Y)?(\\d+M)?(\\d+D)?)?(T(\\d+H)?(\\d+M)?(\\d+S)?)?$")
    @Column("date_range")
    private String dateRange;
}
