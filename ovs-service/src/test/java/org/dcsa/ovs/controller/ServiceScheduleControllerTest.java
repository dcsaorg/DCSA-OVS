package org.dcsa.ovs.controller;

import org.dcsa.ovs.mapping.ServiceMapper;
import org.dcsa.ovs.service.VesselScheduleService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test for service scheduler endpoint")
@WebMvcTest(controllers = {ServiceScheduleController.class})
class ServiceScheduleControllerTest {

  @Autowired MockMvc mockMvc;

  @MockBean VesselScheduleService vesselScheduleService;

  @Spy ServiceMapper vesselScheduleMapper;

  @Test
  @DisplayName("GET service scheduler should return 200 for given basic valid call")
  void testGetServiceSchedulerReturns200ForGivenBasicCall() throws Exception {
    when(vesselScheduleService.findAll(any(), any())).thenReturn(new VesselScheduleService.VesselScheduleTOPage(1, Collections.emptyList()));
    this.mockMvc
        .perform(get("/service-schedules").accept(MediaType.APPLICATION_JSON_VALUE))
        .andDo(print())
        .andExpect(status().isOk())
        .andReturn()
        .getResponse()
        .getContentAsString()
        .equals("[]");
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
            jsonPath("$.errors[0].message").value(containsString("size must be between 0 and 7")));
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
