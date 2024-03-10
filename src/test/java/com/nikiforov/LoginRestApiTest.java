package com.nikiforov;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;

public class LoginRestApiTest {

    @BeforeAll
    static void restAssuredBase() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }
    @Test
    void checkUnsuccessfulLoginTest() {
        given()
                .log().uri()
                .log().method()
                .log().body()
                .contentType(JSON)
                .body("{ \"email\": \"peter@klaven\" }")
                .when()
                .post("/login")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body(matchesJsonSchemaInClasspath("schemas/homework-login-schema.json"))
                .body("error", is("Missing password"));
    }
}
