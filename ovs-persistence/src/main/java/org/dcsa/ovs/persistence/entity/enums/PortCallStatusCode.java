package org.dcsa.ovs.persistence.entity.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum PortCallStatusCode {
  OMIT("Omit"),
  BLNK("Blank"),
  ADHO("Ad Hoc"),
  PHOT("Phase Out"),
  PHIN("Phase In"),
  ROTC("Rotation Change"),
  SLID("Sliding");

  private final String name;
}
