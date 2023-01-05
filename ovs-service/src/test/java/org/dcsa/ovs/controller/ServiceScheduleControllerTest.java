package org.dcsa.ovs.controller;

import org.dcsa.ovs.datafactories.ServiceScheduleTODataFactory;
import org.dcsa.ovs.mapping.ServiceScheduleMapper;
import org.dcsa.ovs.service.VesselScheduleService;
import org.dcsa.skernel.errors.infrastructure.ConcreteRequestErrorMessageExceptionHandler;
import org.dcsa.skernel.errors.infrastructure.FallbackExceptionHandler;
import org.dcsa.skernel.errors.infrastructure.JakartaValidationExceptionHandler;
import org.dcsa.skernel.errors.infrastructure.SpringExceptionHandler;
import org.dcsa.skernel.infrastructure.pagination.PagedResult;
import org.dcsa.skernel.infrastructure.pagination.Paginator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test for service scheduler endpoint")
@WebMvcTest(controllers = {ServiceScheduleController.class})
@Import({
  Paginator.class,
  SpringExceptionHandler.class,
  ConcreteRequestErrorMessageExceptionHandler.class,
  FallbackExceptionHandler.class,
  JakartaValidationExceptionHandler.class
})
class ServiceScheduleControllerTest {

  @Autowired MockMvc mockMvc;

  @MockBean VesselScheduleService vesselScheduleService;

  @Spy ServiceScheduleMapper serviceScheduleMapper;

  @Test
  @DisplayName("GET service scheduler should return 200 for given basic valid call")
  void testGetServiceSchedulerReturns200ForGivenBasicCall() throws Exception {
    when(vesselScheduleService.findAll(any(), any()))
        .thenReturn(new PagedResult(1, ServiceScheduleTODataFactory.serviceScheduleTOList()));
    this.mockMvc
        .perform(get("/service-schedules").accept(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.[0].carrierServiceName").value("A_carrier_service_name"))
        .andExpect(jsonPath("$.[0].carrierServiceCode").value("A_CSC"))
        .andExpect(jsonPath("$.[0].universalServiceReference").value("SR0001D"));
  }

  @Test
  @DisplayName(
      "GET service scheduler should return 400 for invalid carrierServiceCode request param length")
  void testGetServiceSchedulerReturns400ForInvalidCarrierServiceCodeLength() throws Exception {
    this.mockMvc
        .perform(
            get("/service-schedules")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("carrierServiceCode", "x".repeat(6)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.httpMethod").value("GET"))
        .andExpect(jsonPath("$.requestUri").value("/service-schedules"))
        .andExpect(jsonPath("$.errors[0].reason").value("invalidInput"))
        .andExpect(
            jsonPath("$.errors[0].message").value(containsString("size must be between 0 and 5")));
  }

  @Test
  @DisplayName(
      "GET service scheduler should return 400 for invalid universalServiceReference request param length")
  void testGetServiceSchedulerReturns400ForInvalidUniversalServiceReferenceLength()
      throws Exception {
    this.mockMvc
        .perform(
            get("/service-schedules")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("universalServiceReference", "x".repeat(9)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.httpMethod").value("GET"))
        .andExpect(jsonPath("$.requestUri").value("/service-schedules"))
        .andExpect(jsonPath("$.errors[0].reason").value("invalidInput"))
        .andExpect(
            jsonPath("$.errors[0].message").value(containsString("size must be between 0 and 8")));
  }

  @Test
  @DisplayName(
      "GET service scheduler should return 400 for invalid vesselIMONumber request param length")
  void testGetServiceSchedulerReturns400ForInvalidVesselIMONumberLength() throws Exception {
    this.mockMvc
        .perform(
            get("/service-schedules")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("vesselIMONumber", "x".repeat(8)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.httpMethod").value("GET"))
        .andExpect(jsonPath("$.requestUri").value("/service-schedules"))
        .andExpect(jsonPath("$.errors[0].reason").value("invalidInput"))
        .andExpect(
            jsonPath("$.errors[0].message")
                .value(
                    containsString("findAll.vesselIMONumber must be a valid Vessel IMO Number")));
  }

  @Test
  @DisplayName(
      "GET service scheduler should return 400 for invalid vesselName request param length")
  void testGetServiceSchedulerReturns400ForVesselNameLength() throws Exception {

    this.mockMvc
        .perform(
            get("/service-schedules")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("vesselName", "x".repeat(36)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.httpMethod").value("GET"))
        .andExpect(jsonPath("$.requestUri").value("/service-schedules"))
        .andExpect(jsonPath("$.errors[0].reason").value("invalidInput"))
        .andExpect(
            jsonPath("$.errors[0].message").value(containsString("size must be between 0 and 35")));
  }

  @Test
  @DisplayName(
      "GET service scheduler should return 400 for invalid voyageNumber request param length")
  void testGetServiceSchedulerReturns400ForVoyageNumberLength() throws Exception {

    this.mockMvc
        .perform(
            get("/service-schedules")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("voyageNumber", "x".repeat(51)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.httpMethod").value("GET"))
        .andExpect(jsonPath("$.requestUri").value("/service-schedules"))
        .andExpect(jsonPath("$.errors[0].reason").value("invalidInput"))
        .andExpect(
            jsonPath("$.errors[0].message").value(containsString("size must be between 0 and 50")));
  }

  @Test
  @DisplayName(
      "GET service scheduler should return 400 for invalid UNLocationCode request param length")
  void testGetServiceSchedulerReturns400ForUNLocationCodeLength() throws Exception {

    this.mockMvc
        .perform(
            get("/service-schedules")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("UNLocationCode", "x".repeat(6)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.httpMethod").value("GET"))
        .andExpect(jsonPath("$.requestUri").value("/service-schedules"))
        .andExpect(jsonPath("$.errors[0].reason").value("invalidInput"))
        .andExpect(
            jsonPath("$.errors[0].message").value(containsString("size must be between 0 and 5")));
  }

  @Test
  @DisplayName(
      "GET service scheduler should return 400 for invalid facilitySMDGCode request param length")
  void testGetServiceSchedulerReturns400ForFacilitySMDGCodeLength() throws Exception {

    this.mockMvc
        .perform(
            get("/service-schedules")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .param("UNLocationCode", "x".repeat(6)))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.httpMethod").value("GET"))
        .andExpect(jsonPath("$.requestUri").value("/service-schedules"))
        .andExpect(jsonPath("$.errors[0].reason").value("invalidInput"))
        .andExpect(
            jsonPath("$.errors[0].message").value(containsString("size must be between 0 and 5")));
  }
}
