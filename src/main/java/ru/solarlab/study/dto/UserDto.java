package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * DTO пользователя
 */
@Getter /* lombok автоматически сгенерирует
           метод получения значения */
@Setter /* lombok автоматически сгенерирует
           метод установки значения */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
public class UserDto {

    /**
     * Имя пользователя
     */
    @Schema(description = "Имя пользователя")
    private String username;

    /**
     * Роль пользователя
     */
    @Schema(description = "Роль пользователя")
    private String role;

}
