package org.otaibe.eureka.client;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class TestControllerTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/test")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

}