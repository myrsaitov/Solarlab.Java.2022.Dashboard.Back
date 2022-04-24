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
public class AdvertisementDto {

    @Schema(description = "Идентификатор")
    private Integer id;

    @Schema(description = "Имя")
    private String name;

    @Schema(description = "Время")
    private OffsetDateTime startedAt;

    @Schema(description = "Статус")
    private Status status;

}
