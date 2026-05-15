package com.example.pracJournalApp.api.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherResponse {

    private Current current;

    @Data
    public class Current{

        private int temperature;

        @JsonProperty("weather_description")
        private List<String> weatherDescription;

        @JsonProperty("feelslike")
        private int feelsLike;

    }

}
