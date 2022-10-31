package com.yurueu.event.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(info());
    }

    private Info info() {
        return new Info()
                .title("Technical task - Event")
                .version("1.0.0")
                .description("CRUD operation with event")
                .contact(new Contact()
                        .name("Andrei Yurueu")
                        .email("andrei.yurueu1@gmail.com")
                );
    }
}
