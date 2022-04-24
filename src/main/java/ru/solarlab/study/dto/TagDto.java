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
@Schema(description = "Сущность тага")
public class TagDto {

    @Schema(description = "Идентификатор")
    public Integer id;

    @Schema(description = "Дата и время создания тага")
    public OffsetDateTime createdAt;

    @Schema(description = "Дата и время обновления тага")
    public OffsetDateTime updatedAt;

    @Schema(description = "Текст тага")
    public String text;

    @Schema(description = "Статус")
    public Status status;

}
