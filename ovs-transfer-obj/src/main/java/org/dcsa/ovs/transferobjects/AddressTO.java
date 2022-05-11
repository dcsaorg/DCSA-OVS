package org.dcsa.ovs.transferobjects;

import lombok.Builder;

@Builder
public record AddressTO(
  String name,
  String street,
  String streetNumber,
  String floor,
  String postCode,
  String city,
  String stateRegion,
  String country
) { }
