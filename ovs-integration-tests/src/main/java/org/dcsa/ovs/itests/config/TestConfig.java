package org.dcsa.ovs.itests.config;

import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import org.hamcrest.Matcher;

import static com.github.fge.jsonschema.SchemaVersion.DRAFTV4;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class TestConfig {
  public static Matcher<?> jsonSchemaValidator(String fileName) {
    return matchesJsonSchemaInClasspath("schema/" + fileName + ".json")
        .using(
            JsonSchemaFactory.newBuilder()
                .setValidationConfiguration(
                    ValidationConfiguration.newBuilder().setDefaultVersion(DRAFTV4).freeze())
                .freeze());
  }
}
