package com.rb.gateway.exception;

import lombok.Data;

import java.time.Instant;

@Data
public class ExceptionResponse {
    private String message;
    private Instant timeStamp;
}
