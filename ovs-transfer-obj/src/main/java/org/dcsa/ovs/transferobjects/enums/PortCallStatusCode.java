package org.dcsa.ovs.transferobjects.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
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
