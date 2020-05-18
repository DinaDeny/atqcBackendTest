package com.atqc;

import io.qameta.allure.Description;
import lombok.extern.log4j.Log4j;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetTest extends RestAPIBaseTest {

    @Test
    @Description("Create Pet with info")

    public void createNewValidPet() {

        given()
                .contentType("application/json")
                .baseUri("https://petstore.swagger.io/v2")
                .header("Access-Token", "1111222validtoken33344444")
                .body("{\n" +
                        "  \"id\": 1,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 1,\n" +
                        "    \"name\": \"dogs\"\n" +
                        "  },\n" +
                        "  \"name\": \"Jack\",\n" +
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
                       .body("name", is("Jack"))
                       .body("status", is("available"))
                       .body("id", notNullValue());
                 }
}