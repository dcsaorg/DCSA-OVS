package org.dcsa.ovs.persistence.repository.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import org.dcsa.ovs.persistence.entity.*;
import org.dcsa.skernel.domain.persistence.entity.Facility;
import org.dcsa.skernel.domain.persistence.entity.Location;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ServiceSpecification {

  private static final DateTimeFormatter DATE_FORMAT =
    new DateTimeFormatterBuilder()
      .appendPattern("yyyy-MM-dd")
      .parseDefaulting(ChronoField.HOUR_OF_DAY, 0)
      .parseDefaulting(ChronoField.MINUTE_OF_HOUR, 0)
      .parseDefaulting(ChronoField.SECOND_OF_MINUTE, 0)
      .parseDefaulting(ChronoField.MILLI_OF_SECOND, 0)
      .parseDefaulting(ChronoField.OFFSET_SECONDS, 0)
      .toFormatter();

  @Builder
  public static class ServiceSchedulesFilters {
    String carrierServiceCode;
    String universalServiceReference;
    String vesselIMONumber;
    String vesselName;
    String voyageNumber;
    String universalVoyageReference;
    String unLocationCode;
    String facilitySMDGCode;
    String startDate;
    String endDate;
  }

  public static Specification<Service> withFilters(final ServiceSchedulesFilters filters) {

    return (root, query, builder) -> {
      boolean needSubquery = false;
      var subquery = query.subquery(UUID.class);
      var subqueryRoot = subquery.from(Service.class);

      subquery.select(subqueryRoot.get(Service_.ID));

      Join<Service, Vessel> serviceVesselJoin = subqueryRoot.join(Service_.VESSELS, JoinType.LEFT);
      Join<Vessel, TransportCall> vesselTransportCallJoin =
          serviceVesselJoin.join(Vessel_.TRANSPORT_CALLS, JoinType.LEFT);
      Join<TransportCall, Voyage> transportCallImportVoyageJoin =
          vesselTransportCallJoin.join(TransportCall_.IMPORT_VOYAGE, JoinType.LEFT);
      Join<TransportCall, Voyage> transportCallExportVoyageJoin =
          vesselTransportCallJoin.join(TransportCall_.EXPORT_VOYAGE, JoinType.LEFT);
      Join<TransportCall, Location> transportCallLocationJoin =
          vesselTransportCallJoin.join(TransportCall_.LOCATION, JoinType.LEFT);

      List<Predicate> predicates = new ArrayList<>();
      List<Predicate> subQueryPredicate = new ArrayList<>();

      if (null != filters.carrierServiceCode) {
        Predicate carrierServiceCodePredicate =
            builder.equal(root.get(Service_.CARRIER_SERVICE_CODE), filters.carrierServiceCode);
        predicates.add(carrierServiceCodePredicate);
      }

      if (null != filters.universalServiceReference) {
        Predicate universalServiceReferencePredicate =
            builder.equal(
                root.get(Service_.UNIVERSAL_SERVICE_REFERENCE), filters.universalServiceReference);
        predicates.add(universalServiceReferencePredicate);
      }

      if (null != filters.vesselIMONumber) {
        Predicate vesselIMONumberPredicate =
            builder.equal(serviceVesselJoin.get(Vessel_.VESSEL_IM_ONUMBER), filters.vesselIMONumber);
        subQueryPredicate.add(vesselIMONumberPredicate);
      }

      if (null != filters.vesselName) {
        Predicate vesselNamePredicate =
            builder.equal(serviceVesselJoin.get(Vessel_.VESSEL_NAME), filters.vesselName);
        subQueryPredicate.add(vesselNamePredicate);
      }

      if (null != filters.voyageNumber) {
        Predicate voyageNumberPredicate =
            builder.or(
                builder.equal(
                    transportCallImportVoyageJoin.get(Voyage_.CARRIER_VOYAGE_NUMBER),
                    filters.voyageNumber),
                builder.equal(
                    transportCallExportVoyageJoin.get(Voyage_.CARRIER_VOYAGE_NUMBER),
                    filters.voyageNumber));
        subQueryPredicate.add(voyageNumberPredicate);
      }

      if (null != filters.universalVoyageReference) {
        Predicate universalVoyageReferencePredicate =
            builder.or(
                builder.equal(
                    transportCallImportVoyageJoin.get(Voyage_.UNIVERSAL_VOYAGE_REFERENCE),
                    filters.universalVoyageReference),
                builder.equal(
                    transportCallExportVoyageJoin.get(Voyage_.UNIVERSAL_VOYAGE_REFERENCE),
                    filters.universalVoyageReference));
        subQueryPredicate.add(universalVoyageReferencePredicate);
      }

      if (null != filters.unLocationCode) {
        Predicate unLocationPredicate =
            builder.equal(
                transportCallLocationJoin.get("UNLocationCode"), filters.unLocationCode);
        subQueryPredicate.add(unLocationPredicate);
      }

      if (null != filters.facilitySMDGCode) {
        Join<Location, Facility> locationFacilityJoin =
          transportCallLocationJoin.join("facility", JoinType.LEFT);
        Predicate facilitySMDGCodePredicate =
            builder.equal(
              locationFacilityJoin.get("facilitySMDGCode"),
                filters.facilitySMDGCode);
        subQueryPredicate.add(facilitySMDGCodePredicate);
      }

      if (null != filters.startDate || null != filters.endDate) {
        Join<TransportCall, TransportEvent> transportCallTransportEventJoin =
            vesselTransportCallJoin.join("timestamps", JoinType.LEFT);

        Predicate dateRangePredicate;

        if (null != filters.startDate && null != filters.endDate) {
          dateRangePredicate =
              builder.and(
                  builder.greaterThanOrEqualTo(
                      transportCallTransportEventJoin.get(TransportEvent_.EVENT_DATE_TIME),
                      LocalDateTime.parse(filters.startDate, DATE_FORMAT).atOffset(ZoneOffset.UTC)),
                  builder.lessThanOrEqualTo(
                      transportCallTransportEventJoin.get(TransportEvent_.EVENT_DATE_TIME),
                      LocalDateTime.parse(filters.endDate, DATE_FORMAT).atOffset(ZoneOffset.UTC)));
        } else if (null != filters.startDate) {
          dateRangePredicate =
              builder.greaterThanOrEqualTo(
                  transportCallTransportEventJoin.get(TransportEvent_.EVENT_DATE_TIME),
                  LocalDateTime.parse(filters.startDate, DATE_FORMAT).atOffset(ZoneOffset.UTC));
        } else {
          assert null != filters.endDate;
          dateRangePredicate =
              builder.lessThanOrEqualTo(
                  transportCallTransportEventJoin.get(TransportEvent_.EVENT_DATE_TIME),
                  LocalDateTime.parse(filters.endDate, DATE_FORMAT).atOffset(ZoneOffset.UTC));
        }

        subQueryPredicate.add(dateRangePredicate);
      }

      if (!subQueryPredicate.isEmpty()) {
        Predicate predicate = builder.and(subQueryPredicate.toArray(Predicate[]::new));
        subquery.where(predicate);
        predicates.add(builder.in(root.get(Service_.ID)).value(subquery));
      }

      return builder.and(predicates.toArray(Predicate[]::new));
    };
  }
}
