package ru.solarlab.study.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;

/**
 * Пользователь не может удалить объявление
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class UserCannotDeleteAdvertisementException extends ValidationException {

    /** Сообщение об ошибке по умолчанию */
    private static final String DEFAULT_ERROR_MESSAGE = "Пользователь не может удалить объявление";

    public UserCannotDeleteAdvertisementException() {

        super(DEFAULT_ERROR_MESSAGE);

    }

}