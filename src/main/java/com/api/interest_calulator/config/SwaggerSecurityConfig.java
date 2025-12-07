package com.api.interest_calculator.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "ApiKeyAuth",
        type = SecuritySchemeType.APIKEY,
        paramName = "X-API-KEY",
        in = SecuritySchemeIn.HEADER
)
@SecurityRequirement(name = "ApiKeyAuth")
public class SwaggerSecurityConfig {
}