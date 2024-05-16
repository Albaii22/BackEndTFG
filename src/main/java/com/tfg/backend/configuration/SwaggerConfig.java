package com.tfg.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.OpenAPI;

@Configuration
@OpenAPIDefinition(info = @Info(title = "QuizzMania", version = "v1"))
public class SwaggerConfig {
    @Bean
    public OpenAPI customizeOpenAPI() {
        return new OpenAPI();
    }
}
