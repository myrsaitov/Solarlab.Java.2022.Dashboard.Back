package ru.solarlab.study.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *  Объект, служащий для удобства вывода ошибки
 */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Getter /* Вы можете добавить аннотацию @Getter и/или @Setter к любому полю, чтобы lombok автоматически сгенерировал методы получения и установки значения. */
public class ValidationErrorResponse {

    private List<String> errors;

}