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
    @Schema(description = "Идентификатор",
            defaultValue = "2")
    private Long id;

    /**
     * Дата и время создания объявления
     */
    @NotBlank
    @Schema(description = "Дата и время создания объявления",
            defaultValue = "2022-05-08T11:25:28.510024600+03:00")
    private OffsetDateTime createdAt;

    /**
     * Дата и время обновления объявления
     */
    @Schema(description = "Дата и время обновления объявления",
            defaultValue = "2022-05-09T11:25:28.510024600+03:00")
    private OffsetDateTime updatedAt;

    /**
     * Заголовок объявления
     */
    @NotBlank
    @CapitalLetter
    @Schema(description = "Заголовок объявления",
            defaultValue = "Заголовок объявления")
    private String title;

    /**
     * Текст объявления
     */
    @NotBlank
    @Schema(description = "Текст объявления",
            defaultValue = "Текст объявления")
    private String body;

    /**
     * Стоимость
     */
    @NotNull
    @Schema(description = "Стоимость",
            defaultValue = "999.99")
    private float price;

    /**
     * Статус объявления
     */
    @NotNull
    @Schema(description = "Статус",
            defaultValue = "ACTIVE")
    private AdvertisementStatus status;

    /**
     * Идентификатор категории
     */
    @Positive
    @Schema(description = "Идентификатор категории",
            defaultValue = "12")
    private Long categoryId;

    /**
     * Идентификаторы связанных тагов
     */
    @NotNull
    @Schema(description = "Идентификаторы связанных тагов",
            defaultValue = "[2,3,4,5,6]")
    private Long tagIds[]; // Long - удобнее с массивами

    /**
     * Владелец
     */
    @Schema(description = "Владелец",
            defaultValue = "user22")
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
        var tagIds1 = Arrays.stream(one.getTagIds()).sorted().toArray();
        var tagIds2 = Arrays.stream(two.getTagIds()).sorted().toArray();
        if(!Arrays.equals(tagIds1, tagIds2)){
            return false;
        }

        if(one.getOwner() != two.getOwner()){
            return false;
        }

        return true;

    }

}