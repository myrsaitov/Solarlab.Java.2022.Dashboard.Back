package ru.solarlab.study.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Исключение, когда объявление с таким Id не найдено
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class AdvertisementNotFoundException extends RuntimeException {

    public AdvertisementNotFoundException(long advertisementId) {

        super(
                String.format(
                        "Объявление с advertisementId=%s не найдено.",
                        advertisementId));

    }

}