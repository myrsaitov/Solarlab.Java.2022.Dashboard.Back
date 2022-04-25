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
@Table(name = "CATEGORY")
@Schema(description = "Сущность категории")
public class Category {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Schema(description = "Идентификатор")
    public Integer id;

    @Column(name = "created_at", nullable = true)
    @Schema(description = "Дата и время создания категории")
    public OffsetDateTime createdAt;

    @Column(name = "updated_at", nullable = true)
    @Schema(description = "Дата и время обновления категории")
    public OffsetDateTime updatedAt;

    @Column(name = "name", nullable = false)
    @Schema(description = "Имя категории")
    public String name;

    @Column(name = "status", nullable = false)
    @Schema(description = "Статус")
    public Status status;

}
