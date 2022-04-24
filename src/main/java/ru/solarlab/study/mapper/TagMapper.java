package ru.solarlab.study.mapper;

import org.apache.commons.lang3.RandomUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.solarlab.study.dto.TagCreateDto;
import ru.solarlab.study.dto.TagDto;
import ru.solarlab.study.dto.TagUpdateDto;
import ru.solarlab.study.dto.Status;
import ru.solarlab.study.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);

    TagDto tagToTagDto(Tag entity);

    Tag tagUpdateRequestToTagView(TagUpdateDto dto);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "status", ignore = true),
//            @Mapping(target="id", expression = "java(generateId())"),
//            @Mapping(target = "status", constant = "NEW")
    })

    Tag toTag(
            TagCreateDto dto, int id);

    @AfterMapping
    default void afterMappingFromCreate(
            @MappingTarget Tag target,
            TagCreateDto source) {

        target.setStatus(Status.NEW);

    }

    default int generateId() {

        return RandomUtils.nextInt();

    }

}
