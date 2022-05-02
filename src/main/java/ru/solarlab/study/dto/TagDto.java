package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.OffsetDateTime;

@Getter /* lombok автоматически сгенерирует
           метод получения значения */
@Setter /* lombok автоматически сгенерирует
           метод установки значения */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Schema(description = "Сущность тага")
public class TagDto {

    @PositiveOrZero
    @Schema(description = "Идентификатор")
    public long id;

    /**
     * Дата и время создания тага
     */
    @NotBlank
    @Schema(description = "Дата и время создания тага")
    public OffsetDateTime createdAt;

    /**
     * Дата и время обновления тага
     */
    @Schema(description = "Дата и время обновления тага")
    public OffsetDateTime updatedAt;

    /**
     * Текст тага
     */
    @NotBlank
    @Schema(description = "Текст тага")
    public String text;

    /**
     * Статус тага
     */
    @NotNull
    @Schema(description = "Статус")
    public TagStatus status;

}