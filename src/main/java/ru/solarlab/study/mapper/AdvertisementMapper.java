package ru.solarlab.study.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.solarlab.study.dto.AdvertisementCreateDto;
import ru.solarlab.study.dto.AdvertisementDto;
import ru.solarlab.study.dto.AdvertisementStatus;
import ru.solarlab.study.dto.AdvertisementUpdateDto;
import ru.solarlab.study.entity.Advertisement;
import ru.solarlab.study.entity.Tag;

import java.time.OffsetDateTime;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper {

    /**
     * Advertisement => AdvertisementDto
     * @param entity
     * @return
     */
    @Mapping(target = "tagId", ignore = true)
    @Mapping(source = "entity.category.id", target = "categoryId")
    AdvertisementDto advertisementToAdvertisementDto(
            Advertisement entity);

    @AfterMapping /* Marks a method to be invoked at
        the end of a generated mapping method, right
        before the last RETURN statement of the
        mapping method */
    default void afterMappingFromToAdvertisementDto(
            @MappingTarget AdvertisementDto target,
            Advertisement source) {

        // Получает список индексов тагов
        var tagIds = source.getTags().stream()
                .map(Tag::getId).collect(Collectors.toList());
        target.setTagId(tagIds.toArray(new Long[0]));

    }

    /**
     * AdvertisementCreateDto => Advertisement
     * @param dto
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Advertisement advertisementCreateDtoToAdvertisement(
            AdvertisementCreateDto dto);

    @AfterMapping /* Marks a method to be invoked at
        the end of a generated mapping method, right
        before the last RETURN statement of the
        mapping method */
    default void afterMappingFromCreate(
            @MappingTarget Advertisement target,
            AdvertisementCreateDto source) {

        // Время создания
        target.setCreatedAt(OffsetDateTime.now());

        // Статус
        target.setStatus(AdvertisementStatus.NEW);

    }

    /**
     * AdvertisementUpdateDto => Advertisement
     * @param dto
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "tags", ignore = true)
    Advertisement advertisementUpdateDtoToAdvertisement(
            @MappingTarget Advertisement entity,
            AdvertisementUpdateDto dto);

    @AfterMapping /* Marks a method to be invoked at
        the end of a generated mapping method, right
        before the last RETURN statement of the
        mapping method */
    default void afterMappingFromUpdate(
            @MappingTarget Advertisement target,
            AdvertisementUpdateDto source) {

        // Время обновления
        target.setUpdatedAt(OffsetDateTime.now());

    }

}