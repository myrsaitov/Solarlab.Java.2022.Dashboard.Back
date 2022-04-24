package ru.solarlab.study.mapper;

import org.apache.commons.lang3.RandomUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.solarlab.study.dto.AdvertisementCreateDto;
import ru.solarlab.study.dto.AdvertisementDto;
import ru.solarlab.study.dto.AdvertisementUpdateDto;
import ru.solarlab.study.dto.Status;
import ru.solarlab.study.entity.Advertisement;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper {
    AdvertisementMapper INSTANCE = Mappers.getMapper(AdvertisementMapper.class);

    AdvertisementDto advertisementToAdvertisementDto(Advertisement entity);

    Advertisement advertisementUpdateRequestToAdvertisementView(AdvertisementUpdateDto dto);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "status", ignore = true),
//            @Mapping(target="id", expression = "java(generateId())"),
//            @Mapping(target = "status", constant = "NEW")
    })

    Advertisement toAdvertisement(
            AdvertisementCreateDto dto, int id);

    @AfterMapping
    default void afterMappingFromCreate(
            @MappingTarget Advertisement target,
            AdvertisementCreateDto source) {

        target.setStatus(Status.NEW);

    }

    default int generateId() {

        return RandomUtils.nextInt();

    }

}
