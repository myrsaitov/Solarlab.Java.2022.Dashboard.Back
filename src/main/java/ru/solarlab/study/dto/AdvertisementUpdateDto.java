package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.solarlab.study.validation.CapitalLetter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter /* Вы можете добавить аннотацию @Getter и/или @Setter к любому полю, чтобы lombok автоматически сгенерировал методы получения и установки значения. */
@Setter /* Вы можете добавить аннотацию @Getter и/или @Setter к любому полю, чтобы lombok автоматически сгенерировал методы получения и установки значения. */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Schema(description = "DTO обновления объявления")
public class AdvertisementUpdateDto {

    @PositiveOrZero
    @Schema(description = "Идентификатор")
    public Integer id;

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

    @NotNull
    @Schema(description = "Статус")
    public Status status;

}
