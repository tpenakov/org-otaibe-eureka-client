package org.otaibe.eureka.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.junit.QuarkusTest;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.otaibe.eureka.client.utils.JsonUtils;
import org.otaibe.eureka.client.web.controller.ActuatorController;
import org.otaibe.eureka.client.web.domain.Info;
import org.otaibe.eureka.client.web.domain.Metrics;

import javax.inject.Inject;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
@Getter
@Slf4j
public class ActuatorControllerTest {

    @ConfigProperty(name = "quarkus.servlet.context-path", defaultValue = StringUtils.EMPTY)
    String contextPath;

    @Inject
    ObjectMapper objectMapper;
    @Inject
    JsonUtils jsonUtils;

    @Test
    public void testHealthEndpoint() {
        given()
                .when().get(getContextPath() + "/health")
                .then()
                .statusCode(200)
                .body(containsString("UP"));
    }

    @Test
    public void testInfoEndpoint() {
        String responseString = given()
                .when().get(getContextPath() + ActuatorController.INFO)
                .then()
                .statusCode(200)
                .extract()
                .asString()
                ;

        log.info("responseString={}", responseString);
        Optional<Info> result = getJsonUtils().readValue(responseString, Info.class, getObjectMapper());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertNotNull(result.get());
        Assertions.assertNotNull(result.get().getGit());
    }

    @Test
    public void testMetricsEndpoint() {
        String responseString = given()
                .when().get(getContextPath() + ActuatorController.METRICS)
                .then()
                .statusCode(200)
                .extract()
                .asString()
                ;

        log.info("responseString={}", responseString);
        Optional<Metrics> result = getJsonUtils().readValue(responseString, Metrics.class, getObjectMapper());
        Assertions.assertTrue(result.isPresent());
        Assertions.assertTrue(result.get().getMemory() > 0);
        Assertions.assertTrue(result.get().getMemoryFree() > 0);
    }



}