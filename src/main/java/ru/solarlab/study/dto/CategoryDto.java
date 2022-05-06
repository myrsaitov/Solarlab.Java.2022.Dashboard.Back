package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.solarlab.study.validation.CapitalLetter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.OffsetDateTime;

/**
 * DTO категории
 */
@Getter /* lombok автоматически сгенерирует
           метод получения значения */
@Setter /* lombok автоматически сгенерирует
           метод установки значения */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Schema(description = "Сущность категории")
public class CategoryDto {

    @Positive
    @Schema(description = "Идентификатор")
    private long id;

    /**
     * Дата и время создания категории
     */
    @NotBlank
    @Schema(description = "Дата и время создания категории")
    private OffsetDateTime createdAt;

    /**
     * Дата и время обновления категории
     */
    @Schema(description = "Дата и время обновления категории")
    private OffsetDateTime updatedAt;

    /**
     * Имя категории
     */
    @NotBlank
    @CapitalLetter
    @Schema(description = "Имя категории")
    private String name;

    /**
     * Статус категории
     */
    @NotNull
    @Schema(description = "Статус")
    private CategoryStatus status;

    /**
     * Идентификатор родительской категории
     */
    @Positive
    @Schema(description = "Идентификатор родительской категории")
    private long parentCategoryId;

}