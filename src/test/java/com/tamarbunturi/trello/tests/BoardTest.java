package com.tamarbunturi.trello.tests;

import static io.restassured.RestAssured.given;
import com.tamarbunturi.trello.config.ConfigReader;
import org.testng.Assert;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

public class BoardTest {
    private String boardId;

    @Test
    public void createBoard(){

         boardId = given()
                .baseUri("https://api.trello.com")
                .queryParam("key", ConfigReader.getApiKey())
                .queryParam("token", ConfigReader.getToken())
                .queryParam("name", "QA Automation Board")
                .queryParam("defaultLists", false)
                .when()
                      .post("/1/boards")
                .then()
                      .statusCode(200)
                      .extract().path("id");

        Assert.assertNotNull(boardId, "Board should not be null");
    }

    @AfterClass
    public void deleteBoard(){

        given()
                .baseUri("https://api.trello.com")
                .queryParam("key", ConfigReader.getApiKey())
                .queryParam("token", ConfigReader.getToken())
                .when()
                      .delete("/1/boards/" + boardId)
                .then()
                      .statusCode(200);
    }
}
