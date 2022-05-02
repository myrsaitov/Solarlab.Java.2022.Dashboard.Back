package ru.solarlab.study.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.solarlab.study.dto.AdvertisementCreateDto;
import ru.solarlab.study.dto.AdvertisementDto;
import ru.solarlab.study.dto.AdvertisementStatus;
import ru.solarlab.study.dto.AdvertisementUpdateDto;
import ru.solarlab.study.entity.Advertisement;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper {

    AdvertisementDto advertisementToAdvertisementDto(Advertisement entity);

    Advertisement advertisementUpdateRequestToAdvertisementView(AdvertisementUpdateDto dto);

    Advertisement toAdvertisement(AdvertisementCreateDto dto);

    @AfterMapping
    default void afterMappingFromCreate(
            @MappingTarget Advertisement target,
            AdvertisementCreateDto source) {

        target.setStatus(AdvertisementStatus.NEW);

    }

}