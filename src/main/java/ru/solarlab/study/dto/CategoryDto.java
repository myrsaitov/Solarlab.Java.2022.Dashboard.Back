package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
