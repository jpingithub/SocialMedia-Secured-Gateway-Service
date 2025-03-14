package com.rb.gateway.filter;

import com.rb.gateway.exception.ExceptionResponse;
import com.rb.gateway.exception.UnauthorizedException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Component
@Slf4j
public class AuthorizationFilter extends AbstractGatewayFilterFactory<AuthorizationFilter.Config> {

    private final Routing routing;
    private final WebClient.Builder webClientBuilder;

    @Value("${customized-header-for-token}")
    private String customHeaderName;

    public static class Config {
        // Configuration properties here
    }

    public AuthorizationFilter(Routing routing, WebClient.Builder webClientBuilder) {
        super(Config.class);
        this.routing = routing;
        this.webClientBuilder = webClientBuilder;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            log.info("Received request path is : {}", request.getURI().getPath());
            if (routing.isSecuredEndpoint(request.getURI().getPath())){
                log.info("Attempting to hit the SECURED endpoint");
                String fullToken = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                if (fullToken==null || fullToken.isEmpty()) {
                    log.info("Token not found ............ ");
                    throw new UnauthorizedException("No token found");
                }
                String[] tokenStrings = fullToken.split(" ");
                if(tokenStrings.length!=2 && !tokenStrings[0].equalsIgnoreCase("Bearer")){
                    log.info("Failed the basic format of token : {}",fullToken);
                    throw new UnauthorizedException("Invalid token format");
                }else {
                    String[] tokenParts = tokenStrings[1].split("\\.");
                    if(tokenParts.length!=3){
                        log.info("Missing Header/Payload/Signature in the token : {}",tokenStrings[1]);
                        throw new UnauthorizedException("Missing part(s) in the token");
                    }
                }
                return webClientBuilder.build()
                        .get()
                        .uri("lb://AUTH-SERVICE/api/v1/auth/validate?token=" + tokenStrings[1])
                        .retrieve()
                        .onStatus(HttpStatusCode::is4xxClientError, clientResponse ->
                                clientResponse.bodyToMono(ExceptionResponse.class)
                                        .flatMap(errorBody -> {
                                            log.error("Client error during token validation: {}", errorBody.getMessage());
                                            return Mono.error(new UnauthorizedException(errorBody.getMessage()));
                                        })
                        )
                        .bodyToMono(AuthResponse.class)
                        .flatMap(authResponse -> {
                            if (Boolean.TRUE.equals(authResponse.getIsValid())) {
                                log.info("Your token is valid");
                                ServerHttpRequest serverHttpRequest = exchange.getRequest()
                                        .mutate()
                                        .header(customHeaderName, authResponse.getUserName())
                                        .build();
                                return chain.filter(exchange.mutate().request(serverHttpRequest).build());
                            } else {
                                log.info("Invalid token found: {}", tokenStrings[1]);
                                return Mono.error(new UnauthorizedException("Token is not valid to move forward..."));
                            }
                        });
            } else{
                log.info("No restrictions are there public endpoints");
            }
            return chain.filter(exchange);
        };
    }
}