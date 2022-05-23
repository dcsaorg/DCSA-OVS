package org.dcsa.ovs.mapping;

import org.dcsa.ovs.persistence.entity.TransportEvent;
import org.dcsa.ovs.transferobjects.TimestampTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface TimestampMapper {
  @Mappings(
      value = {
        @Mapping(target = "eventTypeCode", source = "typeCode"),
        @Mapping(target = "eventClassifierCode", source = "classifierCode"),
        @Mapping(target = "eventDateTime", source = "dateTime")
      })
  TimestampTO toTO(TransportEvent transportEvent);
}
