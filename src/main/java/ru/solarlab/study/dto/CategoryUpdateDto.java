package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.solarlab.study.validation.CapitalLetter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * DTO обновления категории
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
@Schema(description = "Сущность категории")
public class CategoryUpdateDto {

    /**
     * Имя категории
     */
    @NotBlank
    @CapitalLetter
    @Schema(description = "Имя категории",
            defaultValue = "Имя категории")
    private String name;

    /**
     * Статус категории
     */
    @NotNull
    @Schema(description = "Статус",
            defaultValue = "BLOCKED")
    private CategoryStatus status;

    /**
     * Идентификатор родительской категории
     */
    @Positive
    @Schema(description = "Идентификатор родительской категории",
            defaultValue = "12")
    private Long parentCategoryId;

}
