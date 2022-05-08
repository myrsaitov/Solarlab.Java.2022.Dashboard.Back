package ru.solarlab.study.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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

                /* Отключение CSRF protection: Cross-Site Request Forgery —
                   «межсайтовая подделка запроса», также известна как XSRF —
                   вид атак на посетителей веб-сайтов, использующий недостатки
                   протокола HTTP. */
                .csrf().disable()

                /* Страницы, которые должны быть
                   доступны без авторизации */
                .authorizeRequests()
                    .antMatchers(
                        "/", // Main
                        "/oauth/**",
                        "/v3/api-docs.yaml", // API Swagger
                        "/v3/api-docs/**",   // API Swagger
                        "/swagger-ui.html",
                        "/swagger-ui/**")
                    .permitAll()
                .and()

                // https://docs.spring.io/spring-framework/docs/current/javadoc-api/org/springframework/util/AntPathMatcher.html
                // ? matches one character
                //* matches zero or more characters
                //** matches zero or more directories in a path

                /* Запросы, которые должны быть
                   доступны без авторизации */
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET, "/v1/advertisements/")
                        .permitAll()
                    .mvcMatchers(HttpMethod.GET, "v1/advertisements/{advertisementId:^[0-9]+$}")
                        .permitAll()
                    .mvcMatchers(HttpMethod.GET, "v1/advertisements/tags/{tagId:^[0-9]+$}")
                        .permitAll()
                    .mvcMatchers(HttpMethod.GET, "v1/advertisements/categories/{categoryId:^[0-9]+$}")
                        .permitAll()

                /* Остальные запросы доступны только с авторизацией */
                .anyRequest().authenticated();

    }

}