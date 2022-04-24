package ru.solarlab.study.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 *  Объект, служащий для удобства вывода ошибки
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ValidationErrorResponse {

    private List<String> errors;

}