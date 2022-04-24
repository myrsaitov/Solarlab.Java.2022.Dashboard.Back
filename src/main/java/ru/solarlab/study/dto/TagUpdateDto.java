package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO обновления тага")
public class TagUpdateDto {

    @Schema(description = "Идентификатор")
    public Integer id;

    @Schema(description = "Текст тага")
    public String text;

    @Schema(description = "Статус")
    public Status status;

}
