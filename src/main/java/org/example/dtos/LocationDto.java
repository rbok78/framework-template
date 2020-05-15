package org.example.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import java.util.Objects;

public class LocationDto {

  @JsonProperty("post code")
  public String postCode;
  public String country;
  @JsonProperty("country abbreviation")
  public String countryAbbreviation;
  public List<PlaceDto> places;

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    final LocationDto that = (LocationDto) o;
    return Objects.equals(postCode, that.postCode)
        && Objects.equals(country, that.country)
        && Objects.equals(countryAbbreviation, that.countryAbbreviation)
        && Objects.equals(places, that.places);
  }

  @Override
  public int hashCode() {
    return Objects.hash(postCode, country, countryAbbreviation, places);
  }
}
