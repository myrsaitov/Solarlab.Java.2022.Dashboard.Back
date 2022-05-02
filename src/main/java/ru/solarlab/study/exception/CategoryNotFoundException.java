package ru.solarlab.study.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(long categoryId) {

        super(
                String.format(
                        "Категория с categoryId=%s не найдена.",
                        categoryId));

    }

}