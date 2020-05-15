package org.example;

import static io.restassured.RestAssured.given;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import io.qameta.allure.restassured.AllureRestAssured;
import java.util.List;
import org.example.dtos.LocationDto;
import org.example.dtos.PlaceDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@Epic("As a tester, I want to have an example app so that I can test it")
@DisplayName("Example api test suite")
public class ExampleApiTests {

  @Test
  @Story("As a tester, I want to deserialize json to pojo so that I can test it")
  @DisplayName("Deserialize json to pojo test")
  public void deserializeJsonToPojoTest() {

    // Test data prep
    final LocationDto expectedLocation = new LocationDto();
    expectedLocation.postCode = "51000";
    expectedLocation.country = "Malaysia";
    expectedLocation.countryAbbreviation = "MY";
    final PlaceDto expectedPlace = new PlaceDto();
    expectedPlace.placeName = "Kuala Lumpur";
    expectedPlace.longitude = "101.6932";
    expectedPlace.state = "Kuala Lumpur";
    expectedPlace.stateAbbreviation = "KUL";
    expectedPlace.latitude = "3.1911";
    expectedLocation.places = List.of(expectedPlace);

    // Get location via API
    final String url = "http://api.zippopotam.us/MY/51000";
    final LocationDto location = given()
        .filter(new AllureRestAssured())
        .when()
        .get(url)
        .then()
        .extract()
        .response()
        .as(LocationDto.class);

    // Verify query response
    Assertions.assertEquals(expectedLocation, location);
  }
}
