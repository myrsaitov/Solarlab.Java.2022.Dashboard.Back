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
@Schema(description = "Сущность объявления")
public class AdvertisementDto {

    @PositiveOrZero
    @Schema(description = "Идентификатор")
    public long id;

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
    @PositiveOrZero
    @Schema(description = "Идентификатор категории")
    public long categoryId;

}