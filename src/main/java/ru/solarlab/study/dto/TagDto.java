package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.OffsetDateTime;

/**
 * DTO тага
 */
@Getter /* lombok автоматически сгенерирует
           метод получения значения */
@Setter /* lombok автоматически сгенерирует
           метод установки значения */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Builder /* Annotation produces complex builder APIs for your classes.
            Позволяет удобно создавать объекты классов,
            не прописывая конструкторы для каждого поля по-отдельности */
@Schema(description = "Сущность тага")
public class TagDto {

    @Positive
    @Schema(description = "Идентификатор")
    private Long id;

    /**
     * Дата и время создания тага
     */
    @NotBlank
    @Schema(description = "Дата и время создания тага")
    private OffsetDateTime createdAt;

    /**
     * Дата и время обновления тага
     */
    @Schema(description = "Дата и время обновления тага")
    private OffsetDateTime updatedAt;

    /**
     * Текст тага
     */
    @NotBlank
    @Schema(description = "Текст тага")
    private String text;

    /**
     * Статус тага
     */
    @NotNull
    @Schema(description = "Статус")
    private TagStatus status;

}