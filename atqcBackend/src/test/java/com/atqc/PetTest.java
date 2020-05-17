package com.atqc;

import io.qameta.allure.Description;
import lombok.extern.log4j.Log4j;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetTest extends RestAPIBaseTest {

    @Test
    @Description("Create Pet with valid data")

    public void newValidPet() {

        given()
                .contentType("application/json")
                .baseUri("https://petstore.swagger.io/v2")
                .header("Access-Token", "dfhdh=validtoken=gfsdfdfh")
                .body("{\n" +
                        "  \"id\": 0,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 0,\n" +
                        "    \"name\": \"string\"\n" +
                        "  },\n" +
                        "  \"name\": \"doggie\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"available\"\n" +
                        "}")

                .when()
                .post("/pet")

                .then()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("doggie"))
                .body("message", notNullValue());


    }
}