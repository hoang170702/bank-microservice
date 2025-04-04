package com.bank.gatewayserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class GatewayserverApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayserverApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/micro-bank/accounts/**")
                        .filters(f -> f.rewritePath(
                                        "/micro-bank/accounts/(?<segment>.*)",
                                        "/${segment}")
                                .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                                .circuitBreaker(config -> config.setName("accountsCircuitBreaker")
                                        .setFallbackUri("forward:/contact-support")
                                )
                        )
                        .uri("lb://ACCOUNTS")
                )
                .route(p -> p
                        .path("/micro-bank/card/**")
                        .filters(f -> f.rewritePath(
                                "/micro-bank/card/(?<segment>.*)",
                                "/${segment}"
                        )
                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                        .circuitBreaker(config -> config.setName("cardsCircuitBreaker")
                                .setFallbackUri("forward:/contact-support")
                        ))
                        .uri("lb://CARDS")
                )
                .route(p -> p
                        .path("/micro-bank/loans/**")
                        .filters(f -> f.rewritePath(
                                "/micro-bank/loans/(?<segment>.*)",
                                "/${segment}"
                        )
                        .addResponseHeader("X-Response-Time", LocalDateTime.now().toString())
                        .circuitBreaker(config -> config.setName("loansCircuitBreaker")
                                .setFallbackUri("forward:/contact-support")
                        ))
                        .uri("lb://LOANS")
                )
                .build();
    }

}
