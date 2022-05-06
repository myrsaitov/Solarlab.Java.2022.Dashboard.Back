package ru.solarlab.study.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.security.*;

// https://habr.com/ru/post/536388/

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("bearer"))
                .components(new Components()
                        .addSecuritySchemes("bearer",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.OAUTH2)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .flows(new OAuthFlows()
                                                .password(new OAuthFlow()
                                                        .refreshUrl("/oauth/token")
                                                        .tokenUrl("/oauth/token")
                                                        .scopes(new Scopes().addString("tasks", "scope задач"))
                                                ))))
                .info(

                        new Info()
                                .title("Study Service")
                                .description("Study Service")
                                .version("v0.0.1")
                                .contact(

                                    new Contact()
                                            .email("myrsaitov@gmail.com")
                                            .url("myrsaitov.ru")
                                            .name("Konstantin Mirsaitov")

                                )

                );

    }

}