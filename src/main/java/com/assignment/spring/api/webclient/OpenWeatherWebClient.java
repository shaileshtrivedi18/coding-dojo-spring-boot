package com.assignment.spring.api.webclient;

import com.assignment.spring.api.config.OpenWeatherAPIConfiguration;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
public class OpenWeatherWebClient {
    private final OpenWeatherAPIConfiguration apiConfiguration;

    @Bean
    public WebClient webClient() {
        return WebClient.builder().baseUrl(apiConfiguration.getUrl()).build();
    }
}