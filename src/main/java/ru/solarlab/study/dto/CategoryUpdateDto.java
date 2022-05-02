package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.solarlab.study.validation.CapitalLetter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter /* lombok автоматически сгенерирует
           метод получения значения */
@Setter /* lombok автоматически сгенерирует
           метод установки значения */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Schema(description = "Сущность категории")
public class CategoryUpdateDto {

    @PositiveOrZero
    @Schema(description = "Идентификатор")
    public Integer id;

    @NotBlank
    @CapitalLetter
    @Schema(description = "Имя категории")
    public String name;

    @NotNull
    @Schema(description = "Статус")
    public Status status;

}
