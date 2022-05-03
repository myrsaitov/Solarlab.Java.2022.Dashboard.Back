package ru.solarlab.study.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.solarlab.study.dto.*;
import ru.solarlab.study.entity.Category;

import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * Category => CategoryDto
     * @param entity
     * @return
     */
    CategoryDto categoryToCategoryDto(Category entity);

    /**
     * CategoryCreateDto => Category
     * @param dto
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "advertisements", ignore = true)
    Category categoryCreateDtoToCategory(
            CategoryCreateDto dto);

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
            @MappingTarget Category target,
            CategoryCreateDto source) {

        // Время создания
        target.setCreatedAt(OffsetDateTime.now());

        // Статус
        target.setStatus(CategoryStatus.ACTIVE);

    }

    /**
     * CategoryUpdateDto => Category
     * @param dto
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "advertisements", ignore = true)
    Category categoryUpdateDtoToCategory(
            @MappingTarget Category entity,
            CategoryUpdateDto dto);

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
            @MappingTarget Category target,
            CategoryUpdateDto source) {

        // Время обновления
        target.setUpdatedAt(OffsetDateTime.now());

    }

}