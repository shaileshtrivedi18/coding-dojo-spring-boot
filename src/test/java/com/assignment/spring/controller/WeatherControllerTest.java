package com.assignment.spring.controller;

import com.assignment.spring.model.Request;
import com.assignment.spring.model.Response;
import com.assignment.spring.service.WeatherService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.reactive.function.client.WebClientException;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(WeatherController.class)
public class WeatherControllerTest {

    @MockBean
    WeatherService weatherService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetWeather_Success() throws Exception {
        Mockito.when(weatherService.fetchWeather(Mockito.any(Request.class)))
                .thenReturn(weatherResponse());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/weather")
                        .param("city", "Amsterdam"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("{\"id\":252,\"city\":\"Amsterdam\",\"country\":\"NL\",\"temperature\":284.0}"));
    }

    private Response weatherResponse() {
        return Response.builder()
                .id(252)
                .city("Amsterdam")
                .country("NL")
                .temperature(284.0)
                .build();
    }

    @Test
    public void testGetWeather_MissingParameter() throws Exception {
        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/weather"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Required String parameter 'city' is not present")));
    }

    @Test
    public void testGetWeather_Unauthorized() throws Exception {
        WebClientException unAuthorizedException = WebClientResponseException.create(HttpStatus.UNAUTHORIZED.value(), "Unauthorized : Invalid API Key", null, null, null);
        Mockito.when(weatherService.fetchWeather(Mockito.any(Request.class)))
                .thenThrow(unAuthorizedException);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/weather")
                        .param("city", "Amsterdam"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("Unauthorized : Invalid API Key")));
    }

    @Test
    public void testGetWeather_NotFound() throws Exception {
        WebClientException notFoundException = WebClientResponseException.create(HttpStatus.NOT_FOUND.value(), "City not found", null, null, null);
        Mockito.when(weatherService.fetchWeather(Mockito.any(Request.class)))
                .thenThrow(notFoundException);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/weather")
                        .param("city", "Amsterdam"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().is4xxClientError())
                .andExpect(content().string(containsString("404 City not found")));
    }
}