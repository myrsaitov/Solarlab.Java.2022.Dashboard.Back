package ru.solarlab.study.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

/**
 * Конфигурация сервиса ресурсов
 */
@Configuration
@EnableResourceServer
public class OAuth2ResourceServerConfig extends ResourceServerConfigurerAdapter {

    /** Конфигурация endpoint`ов по доступу к ресурсам */
    @Override
    public void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers( /* Страницы, которые должны быть
                                 доступны без авторизации */
                        "/", // Main
                        "/oauth/**",
                        "/v3/api-docs.yaml", // API Swagger
                        "/v3/api-docs/**",   // API Swagger
                        "/swagger-ui.html",
                        "/swagger-ui/**")
                .permitAll()
                .anyRequest()
                .authenticated();

    }

}