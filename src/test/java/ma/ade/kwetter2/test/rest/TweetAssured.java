package ma.ade.kwetter2.test.rest;

import static io.restassured.RestAssured.given;

import org.junit.Test;

public class TweetAssured extends BaseAssured {

    @Test
    public void basicPingTest() {
        given().when().get("/tweet").then().statusCode(200);
    }

}