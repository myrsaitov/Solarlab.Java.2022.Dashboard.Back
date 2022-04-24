package ru.solarlab.study.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

// DTO создания сущности
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    // Идентификатор
    private Integer id;
    // Наименование
    private String name;
    // Время создания
    private OffsetDateTime startedAt;
    // Статус
    private Status status;
}
