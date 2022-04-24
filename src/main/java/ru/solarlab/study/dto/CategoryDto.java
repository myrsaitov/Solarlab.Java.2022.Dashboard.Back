package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data /* @Data - это удобная сокращённая аннотация, которая содержит в себе возможности из @ToString, @EqualsAndHashCode, @Getter / @Setter и @RequiredArgsConstructor */
@Builder /* @Builder annotation produces complex builder APIs for the annotated POJO classes */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Schema(description = "Сущность категории")
public class CategoryDto {

    @Schema(description = "Идентификатор")
    public Integer id;

    @Schema(description = "Дата и время создания категории")
    public OffsetDateTime createdAt;

    @Schema(description = "Дата и время обновления категории")
    public OffsetDateTime updatedAt;

    @Schema(description = "Имя категории")
    public String name;

    @Schema(description = "Статус")
    public Status status;

}
