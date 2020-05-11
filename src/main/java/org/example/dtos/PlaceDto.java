package org.example.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class PlaceDto {
    @JsonProperty("place name")
    public String placeName;
    public String longitude;
    public String state;
    @JsonProperty("state abbreviation")
    public String stateAbbreviation;
    public String latitude;

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final PlaceDto placeDto = (PlaceDto) o;
        return Objects.equals(placeName, placeDto.placeName) &&
                Objects.equals(longitude, placeDto.longitude) &&
                Objects.equals(state, placeDto.state) &&
                Objects.equals(stateAbbreviation, placeDto.stateAbbreviation) &&
                Objects.equals(latitude, placeDto.latitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(placeName, longitude, state, stateAbbreviation, latitude);
    }
}
