package org.dcsa.ovs.itests.v3;

import org.apache.http.HttpStatus;
import org.dcsa.ovs.itests.config.RestAssuredConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ServiceScheduleIT {

  @BeforeAll
  public static void initializeRestAssured() {
    RestAssuredConfigurator.initialize();
  }

  @Test
  void testServiceScheduleFilterCarrierServiceCode() {
    // service can contain duplicate carrier_service_code
    String s =
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("carrierServiceCode", "B_HLC")
            .get("/v3/service-schedules")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .body("size()", is(2))
            .body("[0].carrierServiceCode", equalTo("B_HLC"))
            .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
            .body("[1].carrierServiceCode", equalTo("B_HLC"))
            .body("[1].carrierServiceName", equalTo("B_carrier_service_name_1"))
            .body("[0].universalServiceReference", equalTo("SR00002B"))
            .body("[1].universalServiceReference", equalTo("SR00003C"))
            .body("[0].vessels.size()", is(2))
            .extract()
            .body()
            .asString();

    System.out.println(s);
  }

  @Test
  void testServiceScheduleFilterUniversalServiceReference() {
    // universal_service_reference is unique in service
    String s =
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("universalServiceReference", "SR00002B")
            .get("/v3/service-schedules")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .body("size()", is(1))
            .body("[0].carrierServiceCode", equalTo("B_HLC"))
            .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
            .body("[0].universalServiceReference", equalTo("SR00002B"))
            .body("[0].vessels.size()", is(2))
            .extract()
            .body()
            .asString();

    System.out.println(s);
  }

  @Test
  void testServiceScheduleFilterVesselIMONumber() {
    // vessel_imo_number is unique for a vessel but a service can be linked to multiple vessels
    String s =
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("vesselIMONumber", "9811000")
            .get("/v3/service-schedules")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .body("size()", is(1))
            .body("[0].carrierServiceCode", equalTo("B_HLC"))
            .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
            .body("[0].universalServiceReference", equalTo("SR00002B"))
            .body("[0].vessels.size()", is(1))
            .body("[0].vessels[0].vesselIMONumber", equalTo("9811000"))
            .body("[0].vessels[0].vesselOperatorCarrierSMDGCode", equalTo("EMC"))
            .body("[0].vessels[0].portCalls.size()", is(3))
            .extract()
            .body()
            .asString();

    System.out.println(s);
  }

  @Test
  void testServiceScheduleFilterVesselName() {
    // vessel_name can be duplicated in vessel, possible to be spread across services as in the case
    // below
    String s =
        given()
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .queryParam("vesselName", "King of the Seas")
            .get("/v3/service-schedules")
            .then()
            .assertThat()
            .statusCode(HttpStatus.SC_OK)
            .body("size()", is(2))
            .body("[0].carrierServiceCode", equalTo("B_HLC"))
            .body("[1].carrierServiceCode", equalTo("B_HLC"))
            .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
            .body("[1].carrierServiceName", equalTo("B_carrier_service_name_1"))
            .body("[0].universalServiceReference", equalTo("SR00002B"))
            .body("[1].universalServiceReference", equalTo("SR00003C"))
            .body("[0].vessels.size()", is(1))
            .body("[0].vessels[0].vesselIMONumber", equalTo("1234567"))
            .body("[0].vessels[0].vesselOperatorCarrierSMDGCode", equalTo("MSK"))
            .body("[0].vessels[0].vesselName", equalTo("King of the Seas"))
            .body("[1].vessels.size()", is(1))
            .body("[1].vessels[0].vesselIMONumber", equalTo("9136307"))
            .body("[1].vessels[0].vesselOperatorCarrierSMDGCode", equalTo("ONE"))
            .body("[1].vessels[0].vesselName", equalTo("King of the Seas"))
            .extract()
            .body()
            .asString();

    System.out.println(s);
  }

  @Test
  void testServiceScheduleFilterVoyageNumber() {
    given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .queryParam("voyageNumber", "4419W")
        .get("/v3/service-schedules")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(1))
        .body("[0].carrierServiceCode", equalTo("B_HLC"))
        .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
        .body("[0].universalServiceReference", equalTo("SR00002B"))
        .body("[0].vessels.size()", is(1))
        .body("[0].vessels[0].vesselIMONumber", equalTo("9811000"))
        .body("[0].vessels[0].vesselOperatorCarrierSMDGCode", equalTo("EMC"))
        .body("[0].vessels[0].vesselName", equalTo("Ever Given"))
        .body("[0].vessels[0].portCalls[0].importVoyageNumber", equalTo("4419W"))
        .extract()
        .body()
        .asString();
  }

  @Test
  void testServiceScheduleFilterUniversalVoyageReference() {
    given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .queryParam("universalVoyageReference", "UVR01")
        .get("/v3/service-schedules")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(1))
        .body("[0].carrierServiceCode", equalTo("B_HLC"))
        .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
        .body("[0].universalServiceReference", equalTo("SR00002B"))
        .body("[0].vessels.size()", is(1))
        .body("[0].vessels[0].vesselIMONumber", equalTo("9811000"))
        .body("[0].vessels[0].vesselOperatorCarrierSMDGCode", equalTo("EMC"))
        .body("[0].vessels[0].vesselName", equalTo("Ever Given"))
        .body("[0].vessels[0].portCalls[0].importUniversalVoyageReference", equalTo("UVR01"))
        .extract()
        .body()
        .asString();
  }

  @Test
  void testServiceScheduleFilterUnLocationCode() {
    given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .queryParam("UNLocationCode", "JPTYO")
        .get("/v3/service-schedules")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(1))
        .body("[0].carrierServiceCode", equalTo("B_HLC"))
        .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
        .body("[0].universalServiceReference", equalTo("SR00002B"))
        .body("[0].vessels.size()", is(1))
        .body("[0].vessels[0].vesselIMONumber", equalTo("9811000"))
        .body("[0].vessels[0].vesselOperatorCarrierSMDGCode", equalTo("EMC"))
        .body("[0].vessels[0].vesselName", equalTo("Ever Given"))
        .body("[0].vessels[0].portCalls[0].importUniversalVoyageReference", equalTo("UVR01"))
        .body("[0].vessels[0].portCalls[0].exportUniversalVoyageReference", equalTo("UVR02"))
        .body("[0].vessels[0].portCalls[0].portTerminalLocation.UNLocationCode", equalTo("JPTYO"))
        .body("[0].vessels[0].portCalls[0].portTerminalLocation.locationName", equalTo("Tokyo"))
        .extract()
        .body()
        .asString();
  }

  @Test
  void testServiceScheduleFilterFacilitySMDGCode() {
    given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .queryParam("facilitySMDGCode", "BTP")
        .get("/v3/service-schedules")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(1))
        .body("[0].carrierServiceCode", equalTo("B_HLC"))
        .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
        .body("[0].universalServiceReference", equalTo("SR00002B"))
        .body("[0].vessels.size()", is(1))
        .body("[0].vessels[0].vesselIMONumber", equalTo("9811000"))
        .body("[0].vessels[0].vesselOperatorCarrierSMDGCode", equalTo("EMC"))
        .body("[0].vessels[0].vesselName", equalTo("Ever Given"))
        .body("[0].vessels[0].portCalls[0].importVoyageNumber", equalTo("A_carrier_voyage_number"))
        .body("[0].vessels[0].portCalls[0].exportVoyageNumber", equalTo("A_carrier_voyage_number"))
        .body("[0].vessels[0].portCalls[0].portTerminalLocation.UNLocationCode", equalTo("BRRIO"))
        .body(
            "[0].vessels[0].portCalls[0].portTerminalLocation.locationName",
            equalTo("Rio de Janeiro"))
        .body("[0].vessels[0].portCalls[0].portTerminalLocation.facilitySMDGCode", equalTo("BTP"))
        .extract()
        .body()
        .asString();
  }

  @Test
  void testServiceScheduleFilterStartDateAndEndDate() {
    given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .queryParam("startDate", "2003-05-02") // yyyy-MM-dd
        .queryParam("endDate", "2003-05-04") // yyyy-MM-dd
        .get("/v3/service-schedules")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(2))
        .body("[0].carrierServiceCode", equalTo("B_HLC"))
        .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
        .body("[0].universalServiceReference", equalTo("SR00002B"))
        .body("[0].vessels.size()", is(1))
        .body("[0].vessels[0].vesselIMONumber", equalTo("1234567"))
        .body("[0].vessels[0].vesselOperatorCarrierSMDGCode", equalTo("MSK"))
        .body("[0].vessels[0].vesselName", equalTo("King of the Seas"))
        .body("[0].vessels[0].portCalls[0].transportCallReference", equalTo("TC-REF-08_02-A"))
        .body(
            "[1].vessels[0].portCalls[0].timestamps[0].eventDateTime", containsString("2003-05-03"))
        .extract()
        .body()
        .asString();
  }

  // combination of 2 params at random
  @Test
  void testServiceScheduleFilterUniversalServiceReferenceAndFacilitySMDGCode() {
    given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .queryParam("universalServiceReference", "SR00002B") // yyyy-MM-dd
        .queryParam("facilitySMDGCode", "BTP") // yyyy-MM-dd
        .get("/v3/service-schedules")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(1))
        .body("[0].carrierServiceCode", equalTo("B_HLC"))
        .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
        .body("[0].universalServiceReference", equalTo("SR00002B"))
        .body("[0].vessels.size()", is(1))
        .body("[0].vessels[0].vesselIMONumber", equalTo("9811000"))
        .body("[0].vessels[0].vesselOperatorCarrierSMDGCode", equalTo("EMC"))
        .body("[0].vessels[0].vesselName", equalTo("Ever Given"))
        .body("[0].vessels[0].portCalls[0].transportCallReference", equalTo("TC-REF-08_03-B"))
        .body("[0].vessels[0].portCalls[0].importVoyageNumber", equalTo("A_carrier_voyage_number"))
        .body("[0].vessels[0].portCalls[0].exportVoyageNumber", equalTo("A_carrier_voyage_number"))
        .body("[0].vessels[0].portCalls[0].portTerminalLocation.UNLocationCode", equalTo("BRRIO"))
        .body(
            "[0].vessels[0].portCalls[0].portTerminalLocation.locationName",
            equalTo("Rio de Janeiro"))
        .body("[0].vessels[0].portCalls[0].portTerminalLocation.facilitySMDGCode", equalTo("BTP"))
        .extract()
        .body()
        .asString();
  }

  @Test
  void testServiceScheduleFilterLimit1() {
    given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .queryParam("limit", "1")
        .get("/v3/service-schedules")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(1))
        .extract()
        .body()
        .asString();
  }

  @Test
  void testServiceScheduleFilterLimit3() {
    given()
        .contentType(MediaType.APPLICATION_JSON_VALUE)
        .queryParam("limit", "3")
        .get("/v3/service-schedules")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(3))
        .extract()
        .body()
        .asString();
  }
}
