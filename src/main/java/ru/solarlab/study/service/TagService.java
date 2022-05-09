package ru.solarlab.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.solarlab.study.dto.TagCreateDto;
import ru.solarlab.study.dto.TagDto;
import ru.solarlab.study.dto.TagUpdateDto;
import ru.solarlab.study.dto.UserDto;
import ru.solarlab.study.entity.Advertisement;
import ru.solarlab.study.entity.Tag;
import ru.solarlab.study.exception.TagNotFoundException;
import ru.solarlab.study.mapper.TagMapper;
import ru.solarlab.study.repository.AdvertisementRepository;
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
     * Объекты репозитория
     */
    private final AdvertisementRepository advertisementRepository;
    private final TagRepository tagRepository;

    /**
     * Максимальное количество на странице
     */
    private static final int DEFAULT_PAGE_SIZE = 1000;

    /**
     * Создает новый таг по данным из DTO
     * @param request DTO для создания тага
     * @return Идентификатор нового тага
     */
    public TagDto create(TagCreateDto request) {

        try {

            // TODO Проверять на наличие повторяющегося тага

            // Создаёт сущность на основе DTO
            Tag tag = tagMapper
                    .tagCreateDtoToTag(request);

            // Сохраняет категорию в БД
            tagRepository.save(tag);

            // Возвращает результат
            return tagMapper
                    .tagToTagDto(tag);

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Обновляет таг с указанным идентификатором по данным из DTO
     * @param tagId Идентификатор обновляемого тага
     * @param request DTO для обновления тага
     * @return Обновление прошло удачно
     */
    public TagDto update(
            long tagId,
            TagUpdateDto request){

        try {

            // С любым статусом
            Tag tag = tagRepository
                    .findById(tagId)
                    .orElseThrow(
                            () -> new TagNotFoundException(tagId));

            tag.setUpdatedAt(OffsetDateTime.now());
            tag.setText(request.getText());
            tag.setStatus(request.getStatus());

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
            long tagId){

        try {

            // С любым статусом
            if (getCurrentUser().getRole().equals("ADMIN")) {

                Tag tag = tagRepository
                        .findById(tagId)
                        .orElseThrow(
                                () -> new TagNotFoundException(tagId));
                return tagMapper.tagToTagDto(tag);

            }
            // Только с активным статусом
            else {

                Tag tag = tagRepository
                        .findActiveById(tagId)
                        .orElseThrow(
                                () -> new TagNotFoundException(tagId));
                return tagMapper.tagToTagDto(tag);

            }


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

        try {

            // Возвращает таги с любым статусом
            if (getCurrentUser().getRole().equals("ADMIN")) {

                return tagRepository
                        .findAll(
                                PageRequest.of(
                                        0,
                                        limit == null ? DEFAULT_PAGE_SIZE : limit))
                        .stream()
                        .map(tagMapper::tagToTagDto)
                        .collect(Collectors.toList());

            }
            // Возвращает таги только с активным статусом
            else {

                return tagRepository
                        .findActive(
                                PageRequest.of(
                                        0,
                                        limit == null ? DEFAULT_PAGE_SIZE : limit))
                        .stream()
                        .map(tagMapper::tagToTagDto)
                        .collect(Collectors.toList());

            }

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Удаляет таг по идентификатору
     * @param tagId Идентификатор тага
     */
    public void deleteById(long tagId){

        try {

            // Возвращает таг с любым статусом
            Tag tag = tagRepository
                .findByIdAndFetchAdvertisements(tagId)
                .orElseThrow(
                              () -> new TagNotFoundException(tagId));


            // Открепляет объявления:

            // Получает список индексов тагов заранее,
            // т.к. нельзя в цикле изменять коллекцию
            var advertisementIds = tag.getAdvertisements().stream()
                    .map(Advertisement::getId).collect(Collectors.toList());

            // Открепляет объявления
            advertisementIds.forEach(
                    advertisementId ->
                    advertisementRepository
                            .findById(advertisementId)
                            .ifPresent(
                                a -> a.removeTag(tagId)));

            // Удаляет таг
            tagRepository.deleteById(tagId);

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Возвращает текущего авторизированного пользователя
     * @return
     */
    private UserDto getCurrentUser() {

        SecurityContext securityContext = SecurityContextHolder.getContext();

        String username = securityContext
                .getAuthentication()
                .getPrincipal()
                .toString();

        String role = securityContext
                .getAuthentication()
                .getAuthorities()
                .stream()
                .findAny()
                .get()
                .getAuthority();

        return new UserDto(username, role);

    }

}