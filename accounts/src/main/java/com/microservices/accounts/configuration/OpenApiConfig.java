package com.microservices.accounts.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "Nguyen Huy Hoang",
                        email = "huyhoang112022gmail.com",
                        url = "https://www.facebook.com/hoangkttk/"
                ),
                description = "Api for project donate charity",
                title = "API - CHARITY",
                version = "1.0",
                license = @License(
                        name = "License name"
                )
        )
        ,
        servers = {
                @Server(
                        description = "LOCAL ENV",
                        url = "http://localhost:8088"
                ),
                @Server(
                        description = "PROD ENV",
                        url = "http://localhost:8088"
                )
        },
        security = {
                @SecurityRequirement(name = "BearerAuth")
        }
)
@SecurityScheme(
        name = "BearerAuth",
        description = "Jwt Bearer Token",
        scheme = "bearer",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {
}
