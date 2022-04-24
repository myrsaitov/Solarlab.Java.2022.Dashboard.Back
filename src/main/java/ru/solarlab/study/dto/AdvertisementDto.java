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
@Schema(description = "Сущность объявления")
public class AdvertisementDto {

    @Schema(description = "Идентификатор")
    public Integer id;

    @Schema(description = "Дата и время создания объявления")
    public OffsetDateTime createdAt;

    @Schema(description = "Дата и время обновления объявления")
    public OffsetDateTime updatedAt;

    @Schema(description = "Заголовок объявления")
    public String title;

    @Schema(description = "Текст объявления")
    public String body;

    @Schema(description = "Стоимость")
    public Float price;

    @Schema(description = "Статус")
    public Status status;

}
