package ru.solarlab.study.dto;

import lombok.Getter;

/**
 * Статус объявления
 */

@Getter /* lombok автоматически сгенерирует
           метод получения значения */
public enum AdvertisementStatus {

    NEW,
    IN_PROGRESS,
    COMPLETED,
    PAUSED,
    DELETED,
    BLOCKED
    
}