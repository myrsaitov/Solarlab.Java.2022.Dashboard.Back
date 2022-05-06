package ru.solarlab.study.dto;

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
    private String username;

    /**
     * Роль пользователя
     */
    private String role;

}
