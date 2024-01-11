package com.finfellows.global.config;

import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import java.util.List;

@Configuration
public class OpenApiConfig {

    private final String securitySchemeName = "bearerAuth";

    @Bean
    public OpenAPI openAPI(@Value("OpenAPI") String appVersion) {
        Server prodServer = new Server();
        Server localServer = new Server();
        prodServer.setUrl("https://api.finfellows.com");
        localServer.setUrl("http://localhost:8080");
        Info info = new Info().title("FinFellow API").version(appVersion)
                .description("FinFellow 웹 애플리케이션 API입니다.")
                .termsOfService("http://swagger.io/terms/")
                .contact(new Contact().name("FinFellow").email("finfellow2023@gmail.com"))
                .license(new License().name("Apache License Version 2.0")
                        .url("http://www.apache.org/licenses/LICENSE-2.0"));

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(
                        new Components()
                                .addSecuritySchemes(securitySchemeName,
                                        new SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
                .info(info)
                .servers(List.of(prodServer, localServer));
    }

}