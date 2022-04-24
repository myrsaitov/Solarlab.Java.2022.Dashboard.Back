package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.OffsetDateTime;

@Data /* @Data - это удобная сокращённая аннотация, которая содержит в себе возможности из @ToString, @EqualsAndHashCode, @Getter / @Setter и @RequiredArgsConstructor */
@Builder /* @Builder annotation produces complex builder APIs for the annotated POJO classes */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Schema(description = "Сущность тага")
public class TagDto {

    @PositiveOrZero
    @Schema(description = "Идентификатор")
    public Integer id;

    @NotBlank
    @Schema(description = "Дата и время создания тага")
    public OffsetDateTime createdAt;

    @Schema(description = "Дата и время обновления тага")
    public OffsetDateTime updatedAt;

    @NotBlank
    @Schema(description = "Текст тага")
    public String text;

    @NotNull
    @Schema(description = "Статус")
    public Status status;

}
