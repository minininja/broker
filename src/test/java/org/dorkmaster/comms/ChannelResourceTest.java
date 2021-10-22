package org.dorkmaster.comms;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class ChannelResourceTest {

    @Test
    public void testGetChannelEndpoint() {
        given()
          .when().get("/v1/comms/channel")
          .then()
             .statusCode(200)
             .body(is("Hello RESTEasy"));
    }

}