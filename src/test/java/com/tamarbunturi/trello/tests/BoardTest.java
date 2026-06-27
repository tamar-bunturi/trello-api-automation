package com.tamarbunturi.trello.tests;

import static io.restassured.RestAssured.given;
import com.tamarbunturi.trello.config.ConfigReader;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;
import static org.hamcrest.Matchers.hasItem;

public class BoardTest {
    private String boardId;
    private String listId;
    private String cardId;

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

    @Test(dependsOnMethods = "createBoard")
    public void createList(){
        listId = given()
                .baseUri("https://api.trello.com")
                .queryParam("key", ConfigReader.getApiKey())
                .queryParam("token", ConfigReader.getToken())
                .queryParam("name", "To do")
                .queryParam("idBoard", boardId)
                .when()
                .post("/1/lists")
                .then()
                .statusCode(200)
                .extract().path("id");

        Assert.assertNotNull(listId, "List should not be null");
    }

    @Test (dependsOnMethods = "createList")
    public void createCard(){
        cardId = given()
                .baseUri("https://api.trello.com")
                .queryParam("key", ConfigReader.getApiKey())
                .queryParam("token", ConfigReader.getToken())
                .queryParam("name","Write API tests")
                .queryParam("idList", listId)
                .when()
                      .post("/1/cards")
                .then()
                      .statusCode(200)
                      .extract().path("id");
        Assert.assertNotNull(cardId, "Card should not be null");
    }

    @Test
    public void getListsShouldContainCreatedList(){
        given()
                .baseUri("https://api.trello.com")
                .queryParam("key", ConfigReader.getApiKey())
                .queryParam("token", ConfigReader.getToken())
                .when()
                      .get("/1/boards/" + boardId + "/lists")
                .then()
                      .statusCode(200)
                      .body("name", hasItem("To do"));

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
