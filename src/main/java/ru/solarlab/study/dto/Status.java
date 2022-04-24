package ru.solarlab.study.dto;

import lombok.Getter;


@Getter
public enum Status {
    NEW,
    IN_PROGRESS,
    COMPLETED,
    PAUSED,
    DELETED,
    BLOCKED
}
