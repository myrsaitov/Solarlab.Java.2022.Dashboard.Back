package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.solarlab.study.validation.CapitalLetter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter /* lombok автоматически сгенерирует
           метод получения значения */
@Setter /* lombok автоматически сгенерирует
           метод установки значения */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Schema(description = "DTO создания объявления")
public class AdvertisementCreateDto {

    @NotBlank
    @CapitalLetter
    @Schema(description = "Заголовок объявления")
    public String title;

    @NotBlank
    @Schema(description = "Текст объявления")
    public String body;

    @NotNull
    @Schema(description = "Стоимость")
    public Float price;

}
