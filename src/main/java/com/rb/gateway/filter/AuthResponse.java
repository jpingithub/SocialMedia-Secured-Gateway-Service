package com.rb.gateway.filter;

import lombok.Data;

@Data
public class AuthResponse {
    private Boolean isValid;
    private String userName;
}
