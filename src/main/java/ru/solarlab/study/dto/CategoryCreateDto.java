package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.solarlab.study.validation.CapitalLetter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Getter /* lombok автоматически сгенерирует
           метод получения значения */
@Setter /* lombok автоматически сгенерирует
           метод установки значения */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Schema(description = "DTO создания категории")
public class CategoryCreateDto {

    /**
     * Имя категории
     */
    @NotBlank
    @CapitalLetter
    @Schema(description = "Имя категории")
    private String name;

    /**
     * Идентификатор родительской категории
     */
    @PositiveOrZero
    @Schema(description = "Идентификатор родительской категории")
    private long parentCategoryId;

}
