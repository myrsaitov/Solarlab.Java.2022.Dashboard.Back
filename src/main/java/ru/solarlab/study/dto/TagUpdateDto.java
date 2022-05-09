package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * DTO обновления тага
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
@Schema(description = "DTO обновления тага")
public class TagUpdateDto {

    /**
     * Текст тага
     */
    @NotBlank
    @Schema(description = "Текст тага",
            defaultValue = "Текст_Тага")
    private String text;

    /**
     * Статус тага
     */
    @NotNull
    @Schema(description = "Статус",
            defaultValue = "BLOCKED")
    private TagStatus status;

}