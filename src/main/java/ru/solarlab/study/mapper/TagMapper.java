package ru.solarlab.study.mapper;

import org.mapstruct.*;
import ru.solarlab.study.dto.*;
import ru.solarlab.study.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto tagToTagDto(Tag entity);

    Tag tagUpdateRequestToTagView(TagUpdateDto dto);

    Tag toTag(TagCreateDto dto);

    @AfterMapping
    default void afterMappingFromCreate(@MappingTarget Tag target, TagCreateDto source) {
        target.setStatus(Status.NEW);
    }

}