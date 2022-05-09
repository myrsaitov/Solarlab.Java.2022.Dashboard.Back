package ru.solarlab.study.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import ru.solarlab.study.validation.CapitalLetter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.OffsetDateTime;
import java.util.Arrays;

/**
 * DTO объявления
 */
@Getter /* lombok автоматически сгенерирует
           метод получения значения */
@Setter /* lombok автоматически сгенерирует
           метод установки значения */
@NoArgsConstructor /* Создаёт конструктор по умолчанию */
@AllArgsConstructor /* Генерирует конструктор для всех полей класса */
@Builder /* Annotation produces complex builder APIs for your classes
            Позволяет удобно создавать объекты классов,
            не прописывая конструкторы для каждого поля по-отдельности */
@Schema(description = "Сущность объявления")
public class AdvertisementDto {

    @Positive
    @Schema(description = "Идентификатор")
    public Long id;

    /**
     * Дата и время создания объявления
     */
    @NotBlank
    @Schema(description = "Дата и время создания объявления")
    public OffsetDateTime createdAt;

    /**
     * Дата и время обновления объявления
     */
    @Schema(description = "Дата и время обновления объявления")
    public OffsetDateTime updatedAt;

    /**
     * Заголовок объявления
     */
    @NotBlank
    @CapitalLetter
    @Schema(description = "Заголовок объявления")
    public String title;

    /**
     * Текст объявления
     */
    @NotBlank
    @Schema(description = "Текст объявления")
    public String body;

    /**
     * Стоимость
     */
    @NotNull
    @Schema(description = "Стоимость")
    public float price;

    /**
     * Статус объявления
     */
    @NotNull
    @Schema(description = "Статус")
    public AdvertisementStatus status;

    /**
     * Идентификатор категории
     */
    @Positive
    @Schema(description = "Идентификатор категории")
    public Long categoryId;

    /**
     * Идентификаторы связанных тагов
     */
    @NotNull
    @Schema(description = "Идентификаторы связанных тагов")
    public Long tagId[]; // Long - удобнее с массивами

    /**
     * Владелец
     */
    @Schema(description = "Владелец")
    private String owner;


    /**
     * Для тестирования: сравнение двух объектов
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj) {

        if(!(obj instanceof AdvertisementDto)) {

            return false;

        }

        return compareEquality(this,(AdvertisementDto)obj);

    }

    static boolean compareEquality(AdvertisementDto one, AdvertisementDto two){

        if(one.getId() != two.getId()){
            return false;
        }

        if(one.getCreatedAt() != two.getCreatedAt()){
            return false;
        }

        if(one.getUpdatedAt() != two.getUpdatedAt()){
            return false;
        }

        if(one.getTitle() != two.getTitle()){
            return false;
        }

        if(one.getBody() != two.getBody()){
            return false;
        }

        if(one.getPrice() != two.getPrice()){
            return false;
        }

        if(one.getStatus() != two.getStatus()){
            return false;
        }

        if(one.getCategoryId() != two.getCategoryId()){
            return false;
        }

        // Сначала массивы отсортированы
        var tagIds1 = Arrays.stream(one.getTagId()).sorted().toArray();
        var tagIds2 = Arrays.stream(two.getTagId()).sorted().toArray();
        if(!Arrays.equals(tagIds1, tagIds2)){
            return false;
        }

        if(one.getOwner() != two.getOwner()){
            return false;
        }

        return true;

    }

}