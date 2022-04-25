package ru.solarlab.study.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.solarlab.study.dto.AdvertisementCreateDto;
import ru.solarlab.study.dto.AdvertisementDto;
import ru.solarlab.study.dto.AdvertisementUpdateDto;
import ru.solarlab.study.entity.Advertisement;
import ru.solarlab.study.mapper.AdvertisementMapper;
import ru.solarlab.study.repository.AdvertisementRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service /* Компонент бизнес уровня */
@RequiredArgsConstructor /* DI: Генерирует конструктор, принимающий значения для каждого final поля или поля с аннотацией @NonNull. Аргументы конструктора будут сгенерированы в том порядке, в котором поля перечислены в классе. Для @NonNull полей конструктор так же будет проверять, чтобы в него не передали значение null. */
@Data /* @Data - это удобная сокращённая аннотация, которая содержит в себе возможности из @ToString, @EqualsAndHashCode, @Getter / @Setter и @RequiredArgsConstructor */
public class AdvertisementService {

    /**
     * Объект маппера
     */
    private final AdvertisementMapper advertisementMapper;

    /**
     * Объект репозитория
     */
    private final AdvertisementRepository advertisementRepository;

    /**
     * Максимальное количество на странице
     */
    private static final int DEFAULT_PAGE_SIZE = 10;

    /**
     * Создает новое объявление по данным из DTO
     * @param request DTO для создания объявления
     * @return Идентификатор созданного объявления
     */
    public Integer create(AdvertisementCreateDto request) {

        Advertisement advertisement = advertisementMapper.toAdvertisement(request);
        advertisementRepository.save(advertisement);
        return advertisement.id;

    }

    /**
     * Обновляет объявление с указанным идентификатором по данным из DTO
     * @param request DTO для обновления объявления
     * @return Обновление прошло удачно
     */
    public boolean update(
            AdvertisementUpdateDto request) {

        Advertisement advertisement = advertisementMapper
                .advertisementUpdateRequestToAdvertisementView(request);

        advertisementRepository.save(advertisement);

        return true;

    }

    /**
     * Возвращает объявление по идентификатору
     * @param advertisementId Идентификатор объявления
     * @return Объявление
     */
    public AdvertisementDto getById(
            Integer advertisementId) {

        return advertisementMapper
                .advertisementToAdvertisementDto(
                        advertisementRepository
                                .findById(advertisementId)
                                .orElse(null));

    }

    /**
     * Возвращает коллекцию объявлений с пагинацией
     * @param limit количество объявлений на странице
     * @return Коллекция объявлений
     */
    public List<AdvertisementDto> getAdvertisements(
            Integer limit) {

        return advertisementRepository
                .findAll(
                        PageRequest.of(
                                0,
                                limit == null ? DEFAULT_PAGE_SIZE : limit))
                .stream()
                .map(advertisementMapper::advertisementToAdvertisementDto)
                .collect(Collectors.toList());

    }

    /**
     * Удаляет объявление по идентификатору
     * Объявление из базы не удаляется, меняется только статус на "Удалено"
     * @param advertisementId Идентификатор объявления
     */
    public void deleteById(Integer advertisementId) {

        advertisementRepository.deleteById(advertisementId);

    }

}