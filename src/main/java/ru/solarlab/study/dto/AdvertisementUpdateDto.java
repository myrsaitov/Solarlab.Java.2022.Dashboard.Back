package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.solarlab.study.validation.CapitalLetter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data /* @Data - это удобная сокращённая аннотация, которая содержит в себе возможности из @ToString, @EqualsAndHashCode, @Getter / @Setter и @RequiredArgsConstructor */
@Builder /* @Builder annotation produces complex builder APIs for the annotated POJO classes */
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

    @NotBlank
    @Schema(description = "Стоимость")
    public Float price;

    @NotNull
    @Schema(description = "Статус")
    public Status status;

}
