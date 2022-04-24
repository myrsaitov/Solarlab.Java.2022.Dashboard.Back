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
@Schema(description = "Сущность категории")
public class CategoryUpdateDto {

    @Schema(description = "Идентификатор")
    public Integer id;

    @Schema(description = "Имя категории")
    public String name;

    @Schema(description = "Статус")
    public Status status;

}
