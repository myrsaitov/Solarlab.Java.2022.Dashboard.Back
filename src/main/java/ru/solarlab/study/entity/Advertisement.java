package ru.solarlab.study.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.solarlab.study.dto.Status;
import ru.solarlab.study.validation.CapitalLetter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.OffsetDateTime;

@Data /* @Data - это удобная сокращённая аннотация, которая содержит в себе возможности из @ToString, @EqualsAndHashCode, @Getter / @Setter и @RequiredArgsConstructor */
@Builder /* @Builder annotation produces complex builder APIs for the annotated POJO classes */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Schema(description = "Сущность объявления")
public class Advertisement {

    @PositiveOrZero
    @Schema(description = "Идентификатор")
    public Integer id;

    @NotBlank
    @Schema(description = "Дата и время создания объявления")
    public OffsetDateTime createdAt;

    @Schema(description = "Дата и время обновления объявления")
    public OffsetDateTime updatedAt;

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
