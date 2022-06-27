package org.dcsa.ovs.persistence.entity;

import lombok.*;
import org.dcsa.skernel.domain.persistence.entity.BaseVessel;
import org.dcsa.skernel.domain.persistence.entity.Carrier;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor
@Setter
@Getter
@Entity
public class Vessel extends BaseVessel {

  @ToString.Exclude
  @EqualsAndHashCode.Exclude
  @OneToMany(mappedBy = "vessel")
  private Set<TransportCall> portCalls = new LinkedHashSet<>();

  @Builder(toBuilder = true)
  public Vessel(
      UUID id,
      String vesselIMONumber,
      String vesselName,
      String vesselFlag,
      String vesselCallSign,
      Carrier vesselOperatorCarrier,
      Boolean isDummyVessel,
      Float vesselLength,
      Float vesselWidth,
      String dimensionUnit,
      Set<TransportCall> portCalls) {
    this.id = id;
    this.vesselIMONumber = vesselIMONumber;
    this.vesselName = vesselName;
    this.vesselFlag = vesselFlag;
    this.vesselCallSign = vesselCallSign;
    this.vesselOperatorCarrier = vesselOperatorCarrier;
    this.isDummyVessel = isDummyVessel;
    this.vesselLength = vesselLength;
    this.vesselWidth = vesselWidth;
    this.dimensionUnit = dimensionUnit;
    this.portCalls = portCalls;
  }
}
