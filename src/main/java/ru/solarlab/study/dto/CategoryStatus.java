package ru.solarlab.study.dto;

import lombok.Getter;

/**
 * Статус категории
 */

@Getter /* lombok автоматически сгенерирует
           метод получения значения */
public enum CategoryStatus {

    ACTIVE,
    BLOCKED

}