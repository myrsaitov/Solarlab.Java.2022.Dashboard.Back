package ru.solarlab.study.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.solarlab.study.dto.TagCreateDto;
import ru.solarlab.study.dto.TagDto;
import ru.solarlab.study.dto.TagStatus;
import ru.solarlab.study.dto.TagUpdateDto;
import ru.solarlab.study.entity.Tag;

import java.time.OffsetDateTime;

/**
 * Маппер тагов
 */
@Mapper(componentModel = "spring")
public interface TagMapper {

    /**
     * Tag => TagDto
     * @param entity
     * @return
     */
    TagDto tagToTagDto(Tag entity);

    /**
     * TagCreateDto => Tag
     * @param dto
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "advertisements", ignore = true)
    Tag tagCreateDtoToTag(
            TagCreateDto dto);

    @AfterMapping /* Marks a method to be invoked at
        the end of a generated mapping method, right
        before the last RETURN statement of the
        mapping method */
    default void afterMappingFromCreate(
            @MappingTarget Tag target,
            TagCreateDto source) {

        // Время создания
        target.setCreatedAt(OffsetDateTime.now());

        // Статус
        target.setStatus(TagStatus.ACTIVE);

    }

    /**
     * TagUpdateDto => Tag
     * @param dto
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "advertisements", ignore = true)
    Tag tagUpdateDtoToTag(
            @MappingTarget Tag entity,
            TagUpdateDto dto);

    @AfterMapping /* Marks a method to be invoked at
        the end of a generated mapping method, right
        before the last RETURN statement of the
        mapping method */
    default void afterMappingFromUpdate(
            @MappingTarget Tag target,
            TagUpdateDto source) {

        // Время обновления
        target.setUpdatedAt(OffsetDateTime.now());

    }

}