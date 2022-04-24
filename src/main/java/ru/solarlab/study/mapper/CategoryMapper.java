package ru.solarlab.study.mapper;

import org.apache.commons.lang3.RandomUtils;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import ru.solarlab.study.dto.CategoryCreateDto;
import ru.solarlab.study.dto.CategoryDto;
import ru.solarlab.study.dto.CategoryUpdateDto;
import ru.solarlab.study.dto.Status;
import ru.solarlab.study.entity.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDto categoryToCategoryDto(Category entity);

    Category categoryUpdateRequestToCategoryView(CategoryUpdateDto dto);

    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "status", ignore = true),
//            @Mapping(target="id", expression = "java(generateId())"),
//            @Mapping(target = "status", constant = "NEW")
    })

    Category toCategory(
            CategoryCreateDto dto, int id);

    @AfterMapping
    default void afterMappingFromCreate(
            @MappingTarget Category target,
            CategoryCreateDto source) {

        target.setStatus(Status.NEW);

    }

    default int generateId() {

        return RandomUtils.nextInt();

    }

}
