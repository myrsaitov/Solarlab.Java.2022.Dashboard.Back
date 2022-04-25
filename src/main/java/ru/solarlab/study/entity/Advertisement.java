package ru.solarlab.study.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.solarlab.study.dto.Status;
import ru.solarlab.study.validation.CapitalLetter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.OffsetDateTime;

@Data /* @Data - это удобная сокращённая аннотация, которая содержит в себе возможности из @ToString, @EqualsAndHashCode, @Getter / @Setter и @RequiredArgsConstructor */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Entity
@Table(name = "ADVERTISEMENT")
@Schema(description = "Сущность объявления")
public class Advertisement {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Schema(description = "Идентификатор")
    public Integer id;

    @Column(name = "created_at", nullable = true)
    @Schema(description = "Дата и время создания объявления")
    public OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    @Schema(description = "Дата и время обновления объявления")
    public OffsetDateTime updatedAt;

    @Column(name = "title", nullable = false)
    @Schema(description = "Заголовок объявления")
    public String title;

    @Column(name = "body", nullable = false)
    @Schema(description = "Текст объявления")
    public String body;

    @Column(name = "price", nullable = false)
    @Schema(description = "Стоимость")
    public Float price;

    @Column(name = "status", nullable = false)
    @Schema(description = "Статус")
    public Status status;

}
