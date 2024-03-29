package org.dcsa.ovs.security;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.dcsa.ovs.persistence.repository.ServiceRepository;
import org.dcsa.skernel.domain.persistence.repository.AddressRepository;
import org.dcsa.skernel.domain.persistence.repository.FacilityRepository;
import org.dcsa.skernel.domain.persistence.repository.LocationRepository;
import org.dcsa.skernel.domain.persistence.repository.UnLocationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ActiveProfiles("prod")
@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
    properties = {
      "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,"
          + "org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration"
    })
@ContextConfiguration(initializers = {SecurityFlowIT.Initializer.class})
class SecurityFlowIT {

  // mock these as we don't want to load the db for this test
  // can be replaced by another test container later if required.
  @MockBean AddressRepository addressRepository;
  @MockBean FacilityRepository facilityRepository;
  @MockBean LocationRepository locationRepository;
  @MockBean ServiceRepository serviceRepository;
  @MockBean UnLocationRepository unLocationRepository;

  @Autowired TestRestTemplate testRestTemplate;

  static final KeycloakContainer keycloak =
      new KeycloakContainer().withRealmImportFile("keycloak/realm-export.json");

  static class Initializer
      implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
      keycloak.start();
      TestPropertyValues.of(
              "spring.security.oauth2.resourceserver.jwt.issuer-uri="
                  + keycloak.getAuthServerUrl()
                  + "realms/dcsa",
              "dcsa.securityConfig.jwt.audience=account")
          .applyTo(configurableApplicationContext.getEnvironment());
    }
  }

  protected String getBearerToken() {

    try {
      String token;
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

      URI authorizationURI =
          new URIBuilder(keycloak.getAuthServerUrl() + "/realms/dcsa/protocol/openid-connect/token")
              .build();
      RestTemplate client = new RestTemplateBuilder().build();
      MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
      formData.put("grant_type", Collections.singletonList("client_credentials"));
      formData.put("client_id", Collections.singletonList("dcsa-api"));
      formData.put("client_secret", Collections.singletonList("X32D3a5FoiDRLzXzJMCFW5Q7pMcbZh6o"));

      String result =
          client
              .exchange(
                  authorizationURI,
                  HttpMethod.POST,
                  new HttpEntity<>(formData, headers),
                  String.class)
              .getBody();

      JacksonJsonParser jsonParser = new JacksonJsonParser();

      token = "Bearer " + jsonParser.parseMap(result).get("access_token").toString();

      log.info("TOKEN :: {}", token);
      return token;
    } catch (URISyntaxException e) {
      log.error("Can't obtain an access token from Keycloak!", e);
      throw new RuntimeException("Can't obtain an access token from Keycloak!");
    }
  }

  @RestController
  static class DummyController {
    @GetMapping("/dummy")
    public ResponseEntity<String> test() {
      return ResponseEntity.ok().body("It works!");
    }
  }

  @Test
  void validTokenShouldReturnSuccess() {

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", getBearerToken());

    ResponseEntity<String> exchange =
        testRestTemplate.exchange(
            "/dummy", HttpMethod.GET, new HttpEntity<>(headers), String.class);

    assertEquals(HttpStatus.OK, exchange.getStatusCode());
    assertEquals("It works!", exchange.getBody());
  }

  @Test
  void invalidTokenShouldReturnUnauthorized() {

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer xxxxxx.yyyyyy.zzzzzz");

    ResponseEntity<String> exchange =
        testRestTemplate.exchange(
            "/dummy", HttpMethod.GET, new HttpEntity<>(headers), String.class);

    assertEquals(HttpStatus.UNAUTHORIZED, exchange.getStatusCode());
  }
}
