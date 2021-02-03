package com.assignment.spring.service.impl;

import com.assignment.spring.dao.entity.Weather;
import com.assignment.spring.dao.repository.WeatherRepository;
import com.assignment.spring.model.Request;
import com.assignment.spring.model.Response;
import com.assignment.spring.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * Weather Service class : This class is responsible for fetching the weather details from external API and saving it in the database
 */
@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WebClient webClient;
    private final WeatherRepository weatherRepository;

    public Response fetchWeather(Request request){
        com.assignment.spring.api.response.WeatherResponse weatherResponse = fetchWeatherFromService(request);
        save(weatherResponse);
        return mapResponse(weatherResponse);
    }

    private com.assignment.spring.api.response.WeatherResponse fetchWeatherFromService(Request request){
        return webClient.get()
                .uri(uriBuilder -> uriBuilder.build(request.getCity()))
                .retrieve()
                .bodyToMono(com.assignment.spring.api.response.WeatherResponse.class)
                .block();
    }

    private Response mapResponse(com.assignment.spring.api.response.WeatherResponse weatherResponse){
        return Response.builder()
                .city(weatherResponse.getName())
                .country(weatherResponse.getSys().getCountry())
                .id(weatherResponse.getId())
                .temperature(weatherResponse.getMain().getTemp())
                .build();
    }

    /**
     * creates the weather entity object and persist it in the database
     * @param weatherResponse
     */
    private void save(com.assignment.spring.api.response.WeatherResponse weatherResponse){
        weatherRepository.save(
                new Weather()
                .setCity(weatherResponse.getName())
                .setCountry(weatherResponse.getSys().getCountry())
                .setId(weatherResponse.getId())
                .setTemperature(weatherResponse.getMain().getTemp())
        );
    }
}