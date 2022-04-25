package ru.solarlab.study.mapper;

import org.mapstruct.*;
import ru.solarlab.study.dto.TagCreateDto;
import ru.solarlab.study.dto.TagDto;
import ru.solarlab.study.dto.TagUpdateDto;
import ru.solarlab.study.dto.Status;
import ru.solarlab.study.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagDto tagToTagDto(Tag entity);

    Tag tagUpdateRequestToTagView(TagUpdateDto dto, Integer id);

    Tag toTag(TagCreateDto dto);

    @AfterMapping
    default void afterMappingFromCreate(@MappingTarget Tag target, TagCreateDto source) {
        target.setStatus(Status.NEW);
    }

}