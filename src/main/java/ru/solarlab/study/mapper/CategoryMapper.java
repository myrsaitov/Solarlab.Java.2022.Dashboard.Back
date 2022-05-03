package ru.solarlab.study.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import ru.solarlab.study.dto.CategoryCreateDto;
import ru.solarlab.study.dto.CategoryDto;
import ru.solarlab.study.dto.CategoryStatus;
import ru.solarlab.study.dto.CategoryUpdateDto;
import ru.solarlab.study.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * Category => CategoryDto
     * @param entity
     * @return
     */
    CategoryDto categoryToCategoryDto(Category entity);

    /**
     * CategoryUpdateDto => Category
     * @param dto
     * @return
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "advertisements", ignore = true)
    Category categoryUpdateRequestToCategoryView(CategoryUpdateDto dto);

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
    Category toCategory(CategoryCreateDto dto);

    /**
     * FromCreate
     * @param target
     * @param source
     */
    @AfterMapping
    default void afterMappingFromCreate(
        @MappingTarget Category target,
        CategoryCreateDto source) {

        target.setStatus(CategoryStatus.ACTIVE);

    }

}