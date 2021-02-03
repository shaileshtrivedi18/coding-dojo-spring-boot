package com.assignment.spring.model;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponse {
    private int code;
    private String description;
}