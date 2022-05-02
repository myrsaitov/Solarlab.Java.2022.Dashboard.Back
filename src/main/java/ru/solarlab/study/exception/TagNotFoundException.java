package ru.solarlab.study.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TagNotFoundException extends RuntimeException {

    public TagNotFoundException(long tagId) {

        super(
                String.format(
                        "Tag с tagId=%s не найден.",
                        tagId));

    }

}