package com.atqc;

import io.qameta.allure.Description;
import lombok.extern.log4j.Log4j;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetTest extends RestAPIBaseTest {

    @Test
    @Description("Create Pet with Name and status")

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



    @Test
    @Description("Create Pet with Name and status")

    public void createNewValidPet1() {

        given()
                .contentType("application/json")
                .baseUri("https://petstore.swagger.io/v2")
                .header("Access-Token", "1111222validtoken33344444")
                .body("{\n" +
                        "  \"id\": 2,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 2,\n" +
                        "    \"name\": \"dogs\"\n" +
                        "  },\n" +
                        "  \"name\": \"Muchtar\",\n" +
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
                .body("name", is("Muchtar"))
                .body("status", is(not(emptyString())))
                .body("id", is(2));
    }

    @Test
    @Description("Create Pet with Name and status")

    public void createNewValidPet3() {

        String petName = "Murka";
       String status = given()
                .contentType("application/json")
                .baseUri("https://petstore.swagger.io/v2")
                .header("Access-Token", "1111222validtoken33344444")
                .body("{\n" +
                        "  \"id\": 3,\n" +
                        "  \"category\": {\n" +
                        "    \"id\": 3,\n" +
                        "    \"name\": \"cats\"\n" +
                        "  },\n" +
                        "  \"name\": \"Murka\",\n" +
                        "  \"photoUrls\": [\n" +
                        "    \"string\"\n" +
                        "  ],\n" +
                        "  \"tags\": [\n" +
                        "    {\n" +
                        "      \"id\": 0,\n" +
                        "      \"name\": \"string\"\n" +
                        "    }\n" +
                        "  ],\n" +
                        "  \"status\": \"pending\"\n" +
                        "}")

                .when()
                .post("/pet")

                .then()
                .statusCode(200)
                .body("name", equalTo(petName))
                .body("status", is("pending"))
                .body("id", notNullValue())
                .extract().path("status");
       System.out.println("Pet status is: " + status);
    }

    @Test
    @Description("Create Pet with invalid category")
    public void createPetwithInvalidCategory(){

        given()
                .contentType("application/json")
                .baseUri("https://petstore.swagger.io/v2")
                .header("Access-Token", "1111222validtoken33344444")
        .when()
                .post("/pet/flying")
        .then()
                .statusCode(415); //когда ввожу "405" номер ошибки (как в примере в свагере), то фейлиться тест - ??
    }

     @Test(dataProvider = "Invalid statuses")
     @Description("Get pet by invalid status")
     public void getPetByInvalidStatus(String petStatus, int code, String message) {

        given()
                .contentType("application/json")
                .baseUri("https://petstore.swagger.io/v2")
                .header("Access-Token", "1111222validtoken33344444")
        .when()
                .get("/pet/{foundByStatus}", petStatus)
        .then()
                .statusCode(code)   //когда юзаю со статус кодом "200" GET запрос, то тест фейлиться - не пойму почему...
                .body("code", is(404))
                .body("type", is("unknown"))
                .body("message", equalTo(message)); //в тесте не подставляется мессадж - ??

     }

    @Test
    @Description ("Delete pet by ID")
    public void deletePetById(){
        given()
                .contentType("application/json")
                .baseUri("https://petstore.swagger.io/v2")
                .header("Access-Token", "1111222validtoken33344444")
        .when()
                .delete("/pet/1")
        .then()
                .statusCode(200)
                .body("code", is(200))
                .body("type", is("unknown"))
                .body("message", equalTo(1));

    }
     @Test
     @Description ("Delete pet that not found by ID")
     public void deletePetThatNotFoundById(){
         given()
                 .contentType("application/json")
                 .baseUri("https://petstore.swagger.io/v2")
                 .header("Access-Token", "1111222validtoken33344444")
         .when()
                 .delete("/pet/25")
         .then()
                 .statusCode(404);
     }


     @DataProvider(name = "Invalid statuses")
     private Object[] [] provider() {
        return new Object[][] {
                        {"Books", 404, "Pets category not found"},
                        {"Specialbooks!@#$%^&*", 404, "Invalid pets category"},
        };
     }

}