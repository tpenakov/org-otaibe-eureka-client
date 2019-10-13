package org.otaibe.eureka.client;

import io.quarkus.test.junit.QuarkusTest;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.otaibe.eureka.client.utils.ControllerUtils;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@Getter
public class TestControllerTest {

    @Inject
    ControllerUtils controllerUtils;

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get(getControllerUtils().getBasePath() + "/test")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}