package com.rb.gateway.filter;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Routing {

    private final List<String> publicPaths = List.of("/api/v1/users/validate/otp","/api/v1/users/login","/api/v1/users/otp","/api/v1/users/register","/api/v1/auth/login","/api/v1/auth/validate");

    public Boolean isSecuredEndpoint(String endpoint){
        return publicPaths.stream().noneMatch(uri -> uri.equals(endpoint));
    }

}
