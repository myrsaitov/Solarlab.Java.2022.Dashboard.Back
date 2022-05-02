package ru.solarlab.study.error;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *  Объект, служащий для удобства вывода ошибки
 */
@Getter /* lombok автоматически сгенерирует
           метод получения значения */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
public class ValidationErrorResponse {

    private List<String> errors;

}