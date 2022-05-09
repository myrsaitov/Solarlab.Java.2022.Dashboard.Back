package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.solarlab.study.validation.CapitalLetter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.OffsetDateTime;

/**
 * DTO объявления
 */
@Getter /* lombok автоматически сгенерирует
           метод получения значения */
@Setter /* lombok автоматически сгенерирует
           метод установки значения */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Builder /* Annotation produces complex builder APIs for your classes
            Позволяет удобно создавать объекты классов,
            не прописывая конструкторы для каждого поля по-отдельности */
@Schema(description = "Сущность объявления")
public class AdvertisementDto {

    @Positive
    @Schema(description = "Идентификатор")
    public Long id;

    /**
     * Дата и время создания объявления
     */
    @NotBlank
    @Schema(description = "Дата и время создания объявления")
    public OffsetDateTime createdAt;

    /**
     * Дата и время обновления объявления
     */
    @Schema(description = "Дата и время обновления объявления")
    public OffsetDateTime updatedAt;

    /**
     * Заголовок объявления
     */
    @NotBlank
    @CapitalLetter
    @Schema(description = "Заголовок объявления")
    public String title;

    /**
     * Текст объявления
     */
    @NotBlank
    @Schema(description = "Текст объявления")
    public String body;

    /**
     * Стоимость
     */
    @NotNull
    @Schema(description = "Стоимость")
    public float price;

    /**
     * Статус объявления
     */
    @NotNull
    @Schema(description = "Статус")
    public AdvertisementStatus status;

    /**
     * Идентификатор категории
     */
    @Positive
    @Schema(description = "Идентификатор категории")
    public Long categoryId;

    /**
     * Идентификаторы связанных тагов
     */
    @NotNull
    @Schema(description = "Идентификаторы связанных тагов")
    public Long tagId[]; // Long - удобнее с массивами

    /**
     * Владелец
     */
    @Schema(description = "Владелец")
    private String owner;

}