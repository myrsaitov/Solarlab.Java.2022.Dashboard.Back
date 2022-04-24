package ru.solarlab.study.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.solarlab.study.dto.*;

import javax.validation.ValidationException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Data
public class TagService {

    /**
     * Создает новый таг по данным из DTO
     * @param dto DTO для создания тага
     * @return Идентификатор нового тага
     */
    public Integer create(TagCreateDto dto) {

        return RandomUtils.nextInt();

    }

    /**
     * Обновляет таг с указанным идентификатором по данным из DTO
     * @param dto DTO для обновления тага
     */
    public void update(
            TagUpdateDto dto) {

        if (!dto.id.equals(dto.getId()))
            throw new ValidationException("ID is not valid");

    }

    /**
     * Возвращает таг по идентефикатору
     * @param tagId Идентификатор тага
     * @return Таг
     */
    public TagDto getById(
            Integer tagId) {

        return TagDto.builder()
                .id(tagId)
                .text("justDoIt")
                .createdAt(OffsetDateTime.now())
                .status(Status.NEW)
                .build();

    }

    /**
     * Возвращает коллекцию тагов с пагинацией
     * @param limit количество тагов на странице
     * @return Коллекция тагов
     */
    public List<TagDto> getTags(
            Integer limit) {

        List<TagDto> list = getTags();

        return list.stream()
                .limit(limit == null ? Integer.MAX_VALUE : limit)
                .collect(Collectors.toList());

    }

    private List<TagDto> getTags() {

        return List.of(
                TagDto.builder()
                        .id(RandomUtils.nextInt())
                        .text("justDoIt")
                        .createdAt(OffsetDateTime.now())
                        .status(Status.NEW)
                        .build(),
                TagDto.builder()
                        .id(RandomUtils.nextInt())
                        .text("justDoIt2")
                        .createdAt(OffsetDateTime.now())
                        .status(Status.IN_PROGRESS)
                        .build());

    }

    /**
     * Удаляет таг по идентификатору
     * Таг из базы не удаляется, меняется только статус на "Удалено"
     * @param tagId Идентификатор тага
     */
    public void deleteById(Integer tagId) {



    }

}