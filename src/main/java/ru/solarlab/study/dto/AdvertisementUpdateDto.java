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

@Getter /* lombok автоматически сгенерирует
           метод получения значения */
@Setter /* lombok автоматически сгенерирует
           метод установки значения */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Schema(description = "DTO обновления объявления")
public class AdvertisementUpdateDto {

    /**
     * Заголовок объявления
     */
    @NotBlank
    @CapitalLetter
    @Schema(description = "Заголовок объявления")
    private String title;

    /**
     * Текст объявления
     */
    @NotBlank
    @Schema(description = "Текст объявления")
    private String body;

    /**
     * Стоимость
     */
    @NotNull
    @Schema(description = "Стоимость")
    private float price;

    /**
     * Статус объявления
     */
    @NotNull
    @Schema(description = "Статус")
    private AdvertisementStatus status;

    /**
     * Идентификатор категории
     */
    @Positive
    @Schema(description = "Идентификатор категории")
    private long categoryId;

    /**
     * Идентификаторы связанных тагов
     */
    @Schema(description = "Идентификаторы связанных тагов")
    private Long tagId[]; // Long - удобнее с массивами

}
