package ru.solarlab.study.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.solarlab.study.dto.Status;
import ru.solarlab.study.dto.TagCreateDto;
import ru.solarlab.study.dto.TagDto;
import ru.solarlab.study.dto.TagUpdateDto;
import ru.solarlab.study.entity.Tag;
import ru.solarlab.study.mapper.TagMapper;

import javax.validation.ValidationException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service /* Компонент бизнес уровня */
@RequiredArgsConstructor /* DI: Генерирует конструктор, принимающий значения для каждого final поля или поля с аннотацией @NonNull. Аргументы конструктора будут сгенерированы в том порядке, в котором поля перечислены в классе. Для @NonNull полей конструктор так же будет проверять, чтобы в него не передали значение null. */
@Data /* @Data - это удобная сокращённая аннотация, которая содержит в себе возможности из @ToString, @EqualsAndHashCode, @Getter / @Setter и @RequiredArgsConstructor */
public class TagService {

    /**
     * Объект маппера
     */
    private final TagMapper tagMapper;

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
     * Возвращает таг по идентификатору
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

        List<Tag> list = getTags();

        return list.stream()
                .limit(limit == null ? Integer.MAX_VALUE : limit)
                .map(tagMapper::tagToTagDto)
                .collect(Collectors.toList());

    }

    private List<Tag> getTags() {

        return List.of(
                Tag.builder()
                        .id(RandomUtils.nextInt())
                        .text("justDoIt")
                        .createdAt(OffsetDateTime.now())
                        .status(Status.NEW)
                        .build(),
                Tag.builder()
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