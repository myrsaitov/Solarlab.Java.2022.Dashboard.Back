package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.solarlab.study.validation.CapitalLetter;

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
@Schema(description = "Сущность категории")
public class CategoryDto {

    @PositiveOrZero
    @Schema(description = "Идентификатор")
    public long id;

    /**
     * Дата и время создания категории
     */
    @NotBlank
    @Schema(description = "Дата и время создания категории")
    public OffsetDateTime createdAt;

    /**
     * Дата и время обновления категории
     */
    @Schema(description = "Дата и время обновления категории")
    public OffsetDateTime updatedAt;

    /**
     * Имя категории
     */
    @NotBlank
    @CapitalLetter
    @Schema(description = "Имя категории")
    public String name;

    /**
     * Статус категории
     */
    @NotNull
    @Schema(description = "Статус")
    public CategoryStatus status;

}