package org.dcsa.ovs.itests.v3;

import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.dcsa.ovs.itests.config.RestAssuredConfigurator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.dcsa.ovs.itests.config.TestConfig.jsonSchemaValidator;
import static org.hamcrest.Matchers.*;

public class ServiceScheduleIT {

  @BeforeAll
  public static void initializeRestAssured() {
    RestAssuredConfigurator.initialize();
  }

  @Test
  void testServiceScheduleFilterCarrierServiceCode() {
    // service can contain duplicate carrier_service_code
    given()
        .contentType(ContentType.JSON)
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
        .body("[0].universalServiceReference", equalTo("SR00002C"))
        .body("[1].universalServiceReference", equalTo("SR00003D"))
        .body("[0].vesselSchedules.size()", is(2))
        .extract()
        .body()
        .asString();
  }

  @Test
  void testServiceScheduleFilterUniversalServiceReference() {
    // universal_service_reference is unique in service
    given()
        .contentType(ContentType.JSON)
        .queryParam("universalServiceReference", "SR00002C")
        .get("/v3/service-schedules")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(1))
        .body("[0].carrierServiceCode", equalTo("B_HLC"))
        .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
        .body("[0].universalServiceReference", equalTo("SR00002C"))
        .body("[0].vesselSchedules.size()", is(2))
        .extract()
        .body()
        .asString();
  }

  @Test
  void testServiceScheduleFilterVesselIMONumber() {
    // vessel_imo_number is unique for a vessel but a service can be linked to multiple vessels
    given()
        .contentType(ContentType.JSON)
        .queryParam("vesselIMONumber", "9811000")
        .get("/v3/service-schedules")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(1))
        .body("[0].carrierServiceCode", equalTo("B_HLC"))
        .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
        .body("[0].universalServiceReference", equalTo("SR00002C"))
        .body("[0].vesselSchedules.size()", is(1))
        .body("[0].vesselSchedules[0].vesselIMONumber", equalTo("9811000"))
        .body("[0].vesselSchedules[0].vesselOperatorSMDGLinerCode", equalTo("EMC"))
        .body("[0].vesselSchedules[0].transportCalls.size()", is(3))
        .extract()
        .body()
        .asString();
  }

  @Test
  void testServiceScheduleFilterVesselName() {
    // vessel_name can be duplicated in vessel, possible to be spread across services as in the case
    // below
    given()
        .contentType(ContentType.JSON)
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
        .body("[0].universalServiceReference", equalTo("SR00002C"))
        .body("[1].universalServiceReference", equalTo("SR00003D"))
        .body("[0].vesselSchedules.size()", is(1))
        .body("[0].vesselSchedules[0].vesselIMONumber", equalTo("1234567"))
        .body("[0].vesselSchedules[0].vesselOperatorSMDGLinerCode", equalTo("MSK"))
        .body("[0].vesselSchedules[0].vesselName", equalTo("King of the Seas"))
        .body("[1].vesselSchedules.size()", is(1))
        .body("[1].vesselSchedules[0].vesselIMONumber", equalTo("9136307"))
        .body("[1].vesselSchedules[0].vesselOperatorSMDGLinerCode", equalTo("ONE"))
        .body("[1].vesselSchedules[0].vesselName", equalTo("King of the Seas"))
        .extract()
        .body()
        .asString();
  }

  @Test
  void testServiceScheduleFilterVoyageNumber() {
    given()
        .contentType(ContentType.JSON)
        .queryParam("voyageNumber", "4419W")
        .get("/v3/service-schedules")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(1))
        .body("[0].carrierServiceCode", equalTo("B_HLC"))
        .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
        .body("[0].universalServiceReference", equalTo("SR00002C"))
        .body("[0].vesselSchedules.size()", is(1))
        .body("[0].vesselSchedules[0].vesselIMONumber", equalTo("9811000"))
        .body("[0].vesselSchedules[0].vesselOperatorSMDGLinerCode", equalTo("EMC"))
        .body("[0].vesselSchedules[0].vesselName", equalTo("Ever Given"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].carrierImportVoyageNumber", equalTo("4419W"))
        .extract()
        .body()
        .asString();
  }

  @Test
  void testServiceScheduleFilterUniversalVoyageReference() {
    given()
        .contentType(ContentType.JSON)
        .queryParam("universalVoyageReference", "UVR01")
        .get("/v3/service-schedules")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(1))
        .body("[0].carrierServiceCode", equalTo("B_HLC"))
        .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
        .body("[0].universalServiceReference", equalTo("SR00002C"))
        .body("[0].vesselSchedules.size()", is(1))
        .body("[0].vesselSchedules[0].vesselIMONumber", equalTo("9811000"))
        .body("[0].vesselSchedules[0].vesselOperatorSMDGLinerCode", equalTo("EMC"))
        .body("[0].vesselSchedules[0].vesselName", equalTo("Ever Given"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].importUniversalVoyageReference",
            equalTo("UVR01"))
        .extract()
        .body()
        .asString();
  }

  @Test
  void testServiceScheduleFilterUnLocationCode() {
    given()
        .contentType(ContentType.JSON)
        .queryParam("UNLocationCode", "JPTYO")
        .get("/v3/service-schedules")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(1))
        .body("[0].carrierServiceCode", equalTo("B_HLC"))
        .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
        .body("[0].universalServiceReference", equalTo("SR00002C"))
        .body("[0].vesselSchedules.size()", is(1))
        .body("[0].vesselSchedules[0].vesselIMONumber", equalTo("9811000"))
        .body("[0].vesselSchedules[0].vesselOperatorSMDGLinerCode", equalTo("EMC"))
        .body("[0].vesselSchedules[0].vesselName", equalTo("Ever Given"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].importUniversalVoyageReference",
            equalTo("UVR01"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].exportUniversalVoyageReference",
            equalTo("UVR02"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].portTerminalLocation.UNLocationCode",
            equalTo("JPTYO"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].portTerminalLocation.locationName",
            equalTo("Tokyo"))
        .extract()
        .body()
        .asString();
  }

  @Test
  void testServiceScheduleFilterFacilitySMDGCode() {
    given()
        .contentType(ContentType.JSON)
        .queryParam("facilitySMDGCode", "BTP")
        .get("/v3/service-schedules")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(1))
        .body("[0].carrierServiceCode", equalTo("B_HLC"))
        .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
        .body("[0].universalServiceReference", equalTo("SR00002C"))
        .body("[0].vesselSchedules.size()", is(1))
        .body("[0].vesselSchedules[0].vesselIMONumber", equalTo("9811000"))
        .body("[0].vesselSchedules[0].vesselOperatorSMDGLinerCode", equalTo("EMC"))
        .body("[0].vesselSchedules[0].vesselName", equalTo("Ever Given"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].carrierImportVoyageNumber",
            equalTo("A_carrier_voyage_number"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].carrierExportVoyageNumber",
            equalTo("A_carrier_voyage_number"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].portTerminalLocation.UNLocationCode",
            equalTo("BRRIO"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].portTerminalLocation.locationName",
            equalTo("Rio de Janeiro"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].portTerminalLocation.facilitySMDGCode",
            equalTo("BTP"))
        .extract()
        .body()
        .asString();
  }

  @Test
  void testServiceScheduleFilterStartDateAndEndDate() {
    given()
        .contentType(ContentType.JSON)
        .queryParam("startDate", "2003-05-02") // yyyy-MM-dd
        .queryParam("endDate", "2003-05-04") // yyyy-MM-dd
        .get("/v3/service-schedules")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(2))
        .body("[0].carrierServiceCode", equalTo("B_HLC"))
        .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
        .body("[0].universalServiceReference", equalTo("SR00002C"))
        .body("[0].vesselSchedules.size()", is(1))
        .body("[0].vesselSchedules[0].vesselIMONumber", equalTo("1234567"))
        .body("[0].vesselSchedules[0].vesselOperatorSMDGLinerCode", equalTo("MSK"))
        .body("[0].vesselSchedules[0].vesselName", equalTo("King of the Seas"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].transportCallReference",
            equalTo("TC-REF-08_02-A"))
        .body(
            "[1].vesselSchedules[0].transportCalls[0].timestamps[0].eventDateTime",
            containsString("2003-05-03"))
        .extract()
        .body()
        .asString();
  }

  // combination of 2 params at random
  @Test
  void testServiceScheduleFilterUniversalServiceReferenceAndFacilitySMDGCode() {
    given()
        .contentType(ContentType.JSON)
        .queryParam("universalServiceReference", "SR00002C") // yyyy-MM-dd
        .queryParam("facilitySMDGCode", "BTP") // yyyy-MM-dd
        .get("/v3/service-schedules")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(1))
        .body("[0].carrierServiceCode", equalTo("B_HLC"))
        .body("[0].carrierServiceName", equalTo("B_carrier_service_name"))
        .body("[0].universalServiceReference", equalTo("SR00002C"))
        .body("[0].vesselSchedules.size()", is(1))
        .body("[0].vesselSchedules[0].vesselIMONumber", equalTo("9811000"))
        .body("[0].vesselSchedules[0].vesselOperatorSMDGLinerCode", equalTo("EMC"))
        .body("[0].vesselSchedules[0].vesselName", equalTo("Ever Given"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].transportCallReference",
            equalTo("TC-REF-08_03-B"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].carrierImportVoyageNumber",
            equalTo("A_carrier_voyage_number"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].carrierExportVoyageNumber",
            equalTo("A_carrier_voyage_number"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].portTerminalLocation.UNLocationCode",
            equalTo("BRRIO"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].portTerminalLocation.locationName",
            equalTo("Rio de Janeiro"))
        .body(
            "[0].vesselSchedules[0].transportCalls[0].portTerminalLocation.facilitySMDGCode",
            equalTo("BTP"))
        .extract()
        .body()
        .asString();
  }

  @Test
  void testServiceScheduleFilterLimit3() {
    given()
        .contentType(ContentType.JSON)
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

  @Test
  void testServiceScheduleFilterLimit1AndValidateSchema() {
    given()
        .contentType(ContentType.JSON)
        .queryParam("limit", "1")
        .get("/v3/service-schedules")
        .then()
        .assertThat()
        .statusCode(HttpStatus.SC_OK)
        .body("size()", is(1))
        .body(jsonSchemaValidator("service-schedule"))
        .extract()
        .body()
        .asString();
  }
}
