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

import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper {

    /**
     * Advertisement => AdvertisementDto
     * @param entity
     * @return
     */
    @Mapping(source = "entity.category.id", target = "categoryId")
    AdvertisementDto advertisementToAdvertisementDto(
            Advertisement entity);

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
    Advertisement advertisementCreateDtoToAdvertisement(
            AdvertisementCreateDto dto);

    /**
     * FromCreate
     * @param target
     * @param source
     */
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
    Advertisement advertisementUpdateDtoToAdvertisement(
            @MappingTarget Advertisement entity,
            AdvertisementUpdateDto dto);

    /**
     * FromUpdate
     * @param target
     * @param source
     */
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