package ru.solarlab.study.dto;

import lombok.Getter;


@Getter /* Вы можете добавить аннотацию @Getter и/или @Setter к любому полю, чтобы lombok автоматически сгенерировал методы получения и установки значения. */
public enum Status {

    NEW,
    IN_PROGRESS,
    COMPLETED,
    PAUSED,
    DELETED,
    BLOCKED
    
}
