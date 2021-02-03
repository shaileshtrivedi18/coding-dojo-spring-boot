package com.assignment.spring.service;

import com.assignment.spring.api.response.Main;
import com.assignment.spring.api.response.Sys;
import com.assignment.spring.api.response.WeatherResponse;
import com.assignment.spring.dao.repository.WeatherRepository;
import com.assignment.spring.model.Request;
import com.assignment.spring.model.Response;
import com.assignment.spring.service.impl.WeatherServiceImpl;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestBodySpec;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersUriSpec;
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class WeatherServiceImplTest {

    private static final int ID = 275;
    private static final String CITY = "Paris";
    private static final String COUNTRY = "FR";
    private static final double TEMPERATURE = 234.5;

    @InjectMocks WeatherServiceImpl weatherService;
    @Mock WebClient webClient;
    @Mock WeatherRepository weatherRepository;

    @Test
    public void testFetchWeather() {

        RequestHeadersUriSpec requestHeadersUriSpec = mock(RequestHeadersUriSpec.class);
        RequestBodySpec requestBodySpec = mock(RequestBodySpec.class);
        ResponseSpec responseSpec = mock(ResponseSpec.class);
        Mono<WeatherResponse> weatherResponseMono = mock(Mono.class);

        when(webClient.get()).thenReturn(requestHeadersUriSpec);
        when(requestHeadersUriSpec.uri(any(Function.class))).thenReturn(requestBodySpec);
        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Mockito.eq(WeatherResponse.class))).thenReturn(weatherResponseMono);
        when(weatherResponseMono.block()).thenReturn(createWeatherAPIResponse());

        Request request = Request.builder().city(CITY).build();
        Response response = weatherService.fetchWeather(request);

        Assertions.assertNotNull(response.getCity());
        Assertions.assertEquals(ID, response.getId().intValue());
        Assertions.assertEquals(TEMPERATURE, response.getTemperature().doubleValue());
        Assertions.assertEquals(CITY, response.getCity());
        Assertions.assertEquals(COUNTRY, response.getCountry());
    }

    private WeatherResponse createWeatherAPIResponse() {
        WeatherResponse weatherResponse = new WeatherResponse();

        weatherResponse.setId(ID);
        weatherResponse.setName(CITY);

        Sys sys = new Sys();
        sys.setCountry(COUNTRY);

        Main main = new Main();
        main.setTemp(TEMPERATURE);

        weatherResponse.setSys(sys);
        weatherResponse.setMain(main);

        return weatherResponse;
    }
}