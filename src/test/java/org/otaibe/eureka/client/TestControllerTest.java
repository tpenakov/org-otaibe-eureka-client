package org.otaibe.eureka.client;

import io.quarkus.test.junit.QuarkusTest;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class TestControllerTest {

    @Getter
    @ConfigProperty(name = "quarkus.servlet.context-path", defaultValue = StringUtils.EMPTY)
    String contextPath;

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get(getContextPath() + "/test")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}