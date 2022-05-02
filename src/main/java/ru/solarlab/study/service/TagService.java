package ru.solarlab.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.solarlab.study.dto.TagCreateDto;
import ru.solarlab.study.dto.TagDto;
import ru.solarlab.study.dto.TagUpdateDto;
import ru.solarlab.study.entity.Tag;
import ru.solarlab.study.mapper.TagMapper;
import ru.solarlab.study.repository.TagRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service /* Компонент бизнес уровня */
@RequiredArgsConstructor /* Генерирует конструктор,
    принимающий значения для каждого final поля или 
    поля с аннотацией @NonNull. 
    Аргументы конструктора будут сгенерированы в том порядке,
    в котором поля перечислены в классе. 
    Для @NonNull полей конструктор так же будет проверять,
    чтобы в него не передали значение null. */
public class TagService {

    /**
     * Объект маппера
     */
    private final TagMapper tagMapper;

    /**
     * Объект репозитория
     */
    private final TagRepository tagRepository;

    /**
     * Максимальное количество на странице
     */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * Создает новый таг по данным из DTO
     * @param request DTO для создания тага
     * @return Идентификатор нового тага
     */
    public Long create(TagCreateDto request) {

        Tag tag = tagMapper.toTag(request);
        tag.createdAt = OffsetDateTime.now();
        tagRepository.save(tag);
        return tag.id;

    }

    /**
     * Обновляет таг с указанным идентификатором по данным из DTO
     * @param tagId Идентификатор обновляемого тага
     * @param request DTO для обновления тага
     * @return Обновление прошло удачно
     */
    public TagDto update(
            long tagId,
            TagUpdateDto request) throws Exception {

        try {

            Tag tag = tagRepository
                    .findById(tagId)
                    .orElseThrow(
                            () -> new Exception("Not Found"));

            tag.updatedAt = OffsetDateTime.now();
            tag.text = request.text;
            tag.status = request.status;

            tagRepository.save(tag);

            return tagMapper.tagToTagDto(tag);

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Возвращает таг по идентификатору
     * @param tagId Идентификатор тага
     * @return Таг
     */
    public TagDto getById(
            long tagId) throws Exception {

        try {

            Tag tag = tagRepository
                    .findById(tagId)
                    .orElseThrow(
                            () -> new Exception("Not Found"));
            return tagMapper.tagToTagDto(tag);

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Возвращает коллекцию тагов с пагинацией
     * @param limit количество тагов на странице
     * @return Коллекция тагов
     */
    public List<TagDto> getTags(
            Integer limit) {

        return tagRepository
                .findAll(
                        PageRequest.of(
                                0,
                                limit == null ? DEFAULT_PAGE_SIZE : limit))
                .stream()
                .map(tagMapper::tagToTagDto)
                .collect(Collectors.toList());

    }

    /**
     * Удаляет таг по идентификатору
     * Таг из базы не удаляется, меняется только статус на "Удалено"
     * @param tagId Идентификатор тага
     */
    public void deleteById(long tagId) throws Exception {

        try {

            Tag tag = tagRepository
                    .findById(tagId)
                    .orElseThrow(
                            () -> new Exception("Not Found"));
            tagRepository.deleteById(tagId);

        }
        catch (Exception ex) {

            throw ex;

        }

    }

}