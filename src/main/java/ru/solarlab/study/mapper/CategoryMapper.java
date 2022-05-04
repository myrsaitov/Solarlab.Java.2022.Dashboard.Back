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

import java.time.OffsetDateTime;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    /**
     * Category => CategoryDto
     * @param source Исходная сущность
     * @return CategoryDto
     */
    @Mapping(target = "parentCategoryId", ignore = true)
    CategoryDto categoryToCategoryDto(Category source);

    @AfterMapping /* Marks a method to be invoked at
        the end of a generated mapping method, right
        before the last RETURN statement of the
        mapping method */
    default void afterMappingFromToCategoryDto(
            @MappingTarget CategoryDto target,
            Category source) {

        // Получает идентификатор родительской категории
        if (source.getParentCategory() != null) {

            target.setParentCategoryId(
                    source.getParentCategory().getId());

        }

    }

    /**
     * CategoryCreateDto => Category
     * @param dto Исходный DTO
     * @return Сущность категории
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "advertisements", ignore = true)
    @Mapping(target = "parentCategory", ignore = true)
    @Mapping(target = "subCategories", ignore = true)
    Category categoryCreateDtoToCategory(
            CategoryCreateDto dto);

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
     * @param dto DTO
     * @return Сущность категории
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "advertisements", ignore = true)
    @Mapping(target = "parentCategory", ignore = true)
    @Mapping(target = "subCategories", ignore = true)
    Category categoryUpdateDtoToCategory(
            @MappingTarget Category entity,
            CategoryUpdateDto dto);

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