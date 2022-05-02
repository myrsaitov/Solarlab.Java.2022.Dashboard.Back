package ru.solarlab.study.mapper;

import org.apache.commons.lang3.RandomUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.solarlab.study.dto.*;
import ru.solarlab.study.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto categoryToCategoryDto(Category entity);

    Category categoryUpdateRequestToCategoryView(CategoryUpdateDto dto);

    Category toCategory(CategoryCreateDto dto);

    @AfterMapping
    default void afterMappingFromCreate(@MappingTarget Category target, CategoryCreateDto source) {
        target.setStatus(Status.NEW);
    }

}