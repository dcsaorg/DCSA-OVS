package org.dcsa.ovs.transferobjects.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
@Getter
public enum DimensionUnit {
  MTR("Meter"),
  FOT("Foot");

  private final String name;

  public static Optional<DimensionUnit> valueFromString(String value) {
    for (DimensionUnit du : DimensionUnit.values()) {
      if (Objects.equals(value, du.toString())) {
        return Optional.of(du);
      }
    }
    return Optional.empty();
  }
}
