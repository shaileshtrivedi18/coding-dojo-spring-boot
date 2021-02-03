package com.assignment.spring.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Response {
    private final Integer id;
    private final String city;
    private final String country;
    private final Double temperature;
}