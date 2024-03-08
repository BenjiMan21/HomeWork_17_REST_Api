package com.nikiforov;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.assertj.core.api.Assertions.assertThat;

public class RestApiTests {
    @Test
    void checkThePageTest() {
        given()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .body("page", is(1));
    }

    @Test
    void checkThePageWithLogsTest() {
        given()
                .log().all()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().all()
                .body("page", is(1));
    }

    @Test
    void checkThePageWithSomeLogsTest() {
        given()
                .log().uri()
                .log().method()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().status()
                .log().body()
                .body("page", is(1));
    }

    @Test
    void checkThePageWithStatusCodeTest() {
        given()
                .log().uri()
                .log().method()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("page", is(1));
    }

    @Test
    void checkThePageWithJsonSchemaTest() {
        given()
                .log().uri()
                .log().method()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/homework-schema.json"))
                .body("page", is(1));
    }

    @Test
    void checkThePageWithJsonSchemaWithHamcrestTest() {
        Response statusResponse = given()
                .log().uri()
                .log().method()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/homework-schema.json"))
                .extract().response();
        assertThat(statusResponse.path("page"), is(1));
    }

    @Test
    void checkThePageWithJsonSchemaWithAssertJTest() {
        String response = given()
                .log().uri()
                .log().method()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/homework-schema.json"))
                .extract().response().asString();
        assertThat(response)
                .contains("\"page\":1")
                .contains("\"total\":12")
                .contains("\"total_pages\":2");
    }

    @Test
    void checkWithAssertJFromMassiveTest() {
        String response = given()
                .log().uri()
                .log().method()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("schemas/homework-schema.json"))
                .extract().response().asString();
        assertThat(response)
                .contains("\"name\":\"cerulean\"")
                .contains("\"pantone_value\":\"15-4020\"");

    }
}
