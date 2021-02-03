package com.assignment.spring.controller;

import com.assignment.spring.api.response.Weather;
import com.assignment.spring.model.Request;
import com.assignment.spring.service.WeatherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

/**
 * Controller class for handling the fetch-weather requests
 */
@RestController
@RequiredArgsConstructor
@Validated
public class WeatherController {

    private final WeatherService weatherService;

    @GetMapping("/weather")
    public ResponseEntity<Weather> weather(@RequestParam @NotEmpty @Valid String city) {
        Request request = Request.builder().city(city).build();
        return new ResponseEntity(weatherService.fetchWeather(request), HttpStatus.OK);
    }
}