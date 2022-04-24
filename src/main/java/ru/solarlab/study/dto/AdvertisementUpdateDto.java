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
@Schema(description = "DTO обновления объявления")
public class AdvertisementUpdateDto {

    @Schema(description = "Идентификатор")
    public Integer id;

    @Schema(description = "Заголовок объявления")
    public String title;

    @Schema(description = "Текст объявления")
    public String body;

    @Schema(description = "Стоимость")
    public Float price;

    @Schema(description = "Статус")
    public Status status;

}
