package com.assignment.spring.service;

import com.assignment.spring.model.Request;
import com.assignment.spring.model.Response;

public interface WeatherService {
    Response fetchWeather(Request request);
}