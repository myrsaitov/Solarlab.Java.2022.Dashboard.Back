package ru.solarlab.study.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// https://habr.com/ru/post/536388/

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI springShopOpenAPI() {

        return new OpenAPI()
                .info(

                        new Info()
                                .title("Study Service")
                                .description("Study Service")
                                .version("v0.0.1")
                                .contact(

                                    new Contact()
                                            .email("myrsaitov@gmail.com")
                                            .url("mursaitov.ru")
                                            .name("Konstantin Mirsaitov")

                                )

                );

    }

}