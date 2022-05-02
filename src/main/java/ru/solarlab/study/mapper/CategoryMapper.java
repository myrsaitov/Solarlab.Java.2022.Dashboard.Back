package ru.solarlab.study.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import ru.solarlab.study.dto.CategoryCreateDto;
import ru.solarlab.study.dto.CategoryDto;
import ru.solarlab.study.dto.CategoryStatus;
import ru.solarlab.study.dto.CategoryUpdateDto;
import ru.solarlab.study.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryDto categoryToCategoryDto(Category entity);

    Category categoryUpdateRequestToCategoryView(CategoryUpdateDto dto);

    Category toCategory(CategoryCreateDto dto);

    @AfterMapping
    default void afterMappingFromCreate(
            @MappingTarget Category target,
            CategoryCreateDto source) {

        target.setStatus(CategoryStatus.ACTIVE);

    }

}