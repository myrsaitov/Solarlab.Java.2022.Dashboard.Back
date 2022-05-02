package ru.solarlab.study.dto;

import lombok.Getter;

/**
 * Статус тага
 */

@Getter /* lombok автоматически сгенерирует
           метод получения значения */
public enum TagStatus {

    ACTIVE,
    DELETED,
    BLOCKED

}