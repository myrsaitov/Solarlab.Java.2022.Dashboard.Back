package ru.solarlab.study.mapper;

import org.mapstruct.*;
import ru.solarlab.study.dto.*;
import ru.solarlab.study.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {

    /**
     * Tag => TagDto
     * @param entity
     * @return
     */
    TagDto tagToTagDto(Tag entity);

    /**
     * TagUpdateDto => Tag
     * @param dto
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Tag tagUpdateRequestToTagView(TagUpdateDto dto);

    /**
     * TagCreateDto => Tag
     * @param dto
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    Tag toTag(TagCreateDto dto);

    /**
     * FromCreate
     * @param target
     * @param source
     */
    @AfterMapping
    default void afterMappingFromCreate(
            @MappingTarget Tag target,
            TagCreateDto source) {

        target.setStatus(TagStatus.ACTIVE);

    }

}