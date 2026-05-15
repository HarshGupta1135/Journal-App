package com.example.pracJournalApp.service;

import com.example.pracJournalApp.api.response.WeatherResponse;
import com.example.pracJournalApp.cache.AppCache;
import com.example.pracJournalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    @Autowired
    private RedisService redisService;

    @Value("${weather.app.api}")
    private String apiKey;

    public WeatherResponse getWeather(String city){
        try{
            WeatherResponse weatherResponse = redisService.get("weather_of_" + city, WeatherResponse.class);
            if(weatherResponse != null){
                return  weatherResponse;
            }else{
                String finalAPI = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY,city).replace(Placeholders.API_KEY,apiKey);
                ResponseEntity<WeatherResponse> exchange = restTemplate.exchange(finalAPI, HttpMethod.GET, null, WeatherResponse.class);
                if(exchange != null){
                    redisService.set("weather_of_" + city, exchange.getBody(), 300l);
                }
                return exchange.getBody();
            }
        }catch (org.springframework.web.client.HttpClientErrorException e) {
            System.out.println("API ERROR RESPONSE:");
            System.out.println(e.getResponseBodyAsString());
            throw e;
        }
    }

}
