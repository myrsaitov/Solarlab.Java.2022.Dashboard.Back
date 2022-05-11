package ru.solarlab.study.dto;

import lombok.Getter;

/**
 * Параметр сортировки объявления
 */

@Getter /* lombok автоматически сгенерирует
           метод получения значения */
public enum AdvertisementSortBy {

    ID("id"),
    TITLE("title"),
    BODY("body"),
    PRICE("price"),
    CREATED_AT("createdAt"),
    UPDATED_AT("updatedAt"),
    OWNER("owner");

    private String name;

    private AdvertisementSortBy(String name) {

        this.name = name;
    }

    public String getName() {

        return this.name;
    }


/*
    private final String value;

    AdvertisementSortBy(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static AdvertisementSortBy forValue(String value) {
        return Arrays.stream(AdvertisementSortBy.values())
                .filter(s -> s.getValue().equals(value))
                .findFirst()
                .orElseThrow(); // depending on requirements: can be .orElse(null);
    }
*/
}