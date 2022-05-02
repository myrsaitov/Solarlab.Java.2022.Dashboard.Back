package ru.solarlab.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.solarlab.study.dto.AdvertisementCreateDto;
import ru.solarlab.study.dto.AdvertisementDto;
import ru.solarlab.study.dto.AdvertisementUpdateDto;
import ru.solarlab.study.entity.Advertisement;
import ru.solarlab.study.exception.AdvertisementNotFoundException;
import ru.solarlab.study.mapper.AdvertisementMapper;
import ru.solarlab.study.repository.AdvertisementRepository;

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
    public Long create(AdvertisementCreateDto request) {

        Advertisement advertisement = advertisementMapper.toAdvertisement(request);
        advertisement.createdAt = OffsetDateTime.now();
        advertisementRepository.save(advertisement);
        return advertisement.id;

    }

    /**
     * Обновляет объявление с указанным идентификатором по данным из DTO
     * @param advertisementId Идентификатор обновляемого объявления
     * @param request DTO для обновления объявления
     * @return Обновление прошло удачно
     */
    public AdvertisementDto update(
            long advertisementId,
            AdvertisementUpdateDto request){

        try {

            Advertisement advertisement = advertisementRepository
                    .findById(advertisementId)
                    .orElseThrow(
                            () -> new AdvertisementNotFoundException(advertisementId));

            advertisement.updatedAt = OffsetDateTime.now();
            advertisement.title = request.title;
            advertisement.body = request.body;
            advertisement.price = request.price;
            advertisement.status = request.status;

            advertisementRepository.save(advertisement);
            return advertisementMapper.advertisementToAdvertisementDto(advertisement);

        }
        catch (Exception ex) {

            throw ex;

        }

    }

    /**
     * Возвращает объявление по идентификатору
     * @param advertisementId Идентификатор объявления
     * @return Объявление
     */
    public AdvertisementDto getById(
            long advertisementId){

        try {

            Advertisement advertisement = advertisementRepository
                    .findById(advertisementId)
                    .orElseThrow(
                            () -> new AdvertisementNotFoundException(advertisementId));
            return advertisementMapper.advertisementToAdvertisementDto(advertisement);

        }
        catch (Exception ex) {

            throw ex;

        }

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
    public void deleteById(long advertisementId){

        try {

            Advertisement advertisement = advertisementRepository
                    .findById(advertisementId)
                    .orElseThrow(
                            () -> new AdvertisementNotFoundException(advertisementId));
            advertisementRepository.deleteById(advertisementId);

        }
        catch (Exception ex) {

            throw ex;

        }

    }

}