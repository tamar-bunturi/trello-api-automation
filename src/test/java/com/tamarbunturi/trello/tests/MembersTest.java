package com.tamarbunturi.trello.tests;

import static io.restassured.RestAssured.given;
import com.tamarbunturi.trello.config.ConfigReader;
import org.testng.annotations.Test;

public class MembersTest {
    @Test
    public void getMembersMe(){
        given()
                .baseUri("https://api.trello.com")
                .queryParam("key", ConfigReader.getApiKey())
                .queryParam("token", ConfigReader.getToken())

                .when()
                       .get("/1/members/me")
                .then()
                       .statusCode(200);
        }
}
