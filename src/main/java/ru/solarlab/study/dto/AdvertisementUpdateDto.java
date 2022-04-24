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
@Schema(description = "Сущность объявления")
public class AdvertisementUpdateDto {

    @Schema(description = "Идентификатор")
    public Integer id;

    @Schema(description = "Имя")
    public String name;

    @Schema(description = "Время")
    public OffsetDateTime startedAt;

    @Schema(description = "Статус")
    public Status status;

}
