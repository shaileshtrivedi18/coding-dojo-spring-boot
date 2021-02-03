package com.assignment.spring.api.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "config.api.open-weather")
@Data
public class OpenWeatherAPIConfiguration {
    private String url;
}