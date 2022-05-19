package org.dcsa.ovs.transferobjects.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum PortCallStatusCode {
  OMIT("Omit"),
  BLNK("Blank"),
  ADHO("Ad Hoc"),
  PHOT("Phase Out"),
  PHIN("Phase In"),
  SLID("Sliding");

  private final String name;

  public static Optional<PortCallStatusCode> valueFromString(String value) {
    for (PortCallStatusCode pcsc : PortCallStatusCode.values()) {
      if (Objects.equals(value, pcsc.toString())) {
        return Optional.of(pcsc);
      }
    }
    return Optional.empty();
  }
}
