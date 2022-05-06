package ru.solarlab.study.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ValidationException;

/**
 * Нельзя редактировать объявление других пользователей
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class CannotEditOtherAdvertisementException extends ValidationException {

    /** Сообщение об ошибке по умолчанию */
    private static final String DEFAULT_ERROR_MESSAGE = "Нельзя редактировать объявление других пользователей";

    public CannotEditOtherAdvertisementException() {

        super(DEFAULT_ERROR_MESSAGE);

    }

}