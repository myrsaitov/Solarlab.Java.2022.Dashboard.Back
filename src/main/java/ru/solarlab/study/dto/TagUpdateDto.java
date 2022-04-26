package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Getter /* Вы можете добавить аннотацию @Getter и/или @Setter к любому полю, чтобы lombok автоматически сгенерировал методы получения и установки значения. */
@Setter /* Вы можете добавить аннотацию @Getter и/или @Setter к любому полю, чтобы lombok автоматически сгенерировал методы получения и установки значения. */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Schema(description = "DTO обновления тага")
public class TagUpdateDto {

    @PositiveOrZero
    @Schema(description = "Идентификатор")
    public Integer id;

    @NotBlank
    @Schema(description = "Текст тага")
    public String text;

    @NotNull
    @Schema(description = "Статус")
    public Status status;

}
