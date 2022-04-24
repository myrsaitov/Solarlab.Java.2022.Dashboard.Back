package ru.solarlab.study.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Service;
import ru.solarlab.study.dto.AdvertisementCreateDto;
import ru.solarlab.study.dto.AdvertisementDto;
import ru.solarlab.study.dto.AdvertisementUpdateDto;
import ru.solarlab.study.dto.Status;

import javax.validation.ValidationException;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Data
public class AdvertisementService {

    /**
     * Создает новое объявление по данным из DTO
     * @param dto DTO для создания объявления
     * @return Идентификатор созданного объявления
     */
    public Integer create(AdvertisementCreateDto dto) {

        return RandomUtils.nextInt();

    }

    /**
     * Обновляет объявление с указанным идентификатором по данным из DTO
     * @param dto DTO для обновления объявления
     */
    public void update(
            AdvertisementUpdateDto dto) {

        if (!dto.id.equals(dto.getId()))
            throw new ValidationException("ID is not valid");

    }

    /**
     * Возвращает объявление по идентефикатору
     * @param advertisementId Идентификатор объявления
     * @return Объявление
     */
    public AdvertisementDto getById(
            Integer advertisementId) {

        return AdvertisementDto.builder()
                .id(advertisementId)
                .title("justDoIt")
                .createdAt(OffsetDateTime.now())
                .status(Status.NEW)
                .build();

    }

    /**
     * Возвращает коллекцию объявлений с пагинацией
     * @param limit количество объявлений на странице
     * @return Коллекция объявлений
     */
    public List<AdvertisementDto> getAdvertisements(
            Integer limit) {

        List<AdvertisementDto> list = getAdvertisements();

        return list.stream()
                .limit(limit == null ? Integer.MAX_VALUE : limit)
                .collect(Collectors.toList());

    }

    private List<AdvertisementDto> getAdvertisements() {

        return List.of(
                AdvertisementDto.builder()
                        .id(RandomUtils.nextInt())
                        .title("justDoIt")
                        .createdAt(OffsetDateTime.now())
                        .status(Status.NEW)
                        .build(),
                AdvertisementDto.builder()
                        .id(RandomUtils.nextInt())
                        .title("justDoIt2")
                        .createdAt(OffsetDateTime.now())
                        .status(Status.IN_PROGRESS)
                        .build());

    }

    /**
     * Удаляет объявление по идентификатору
     * Объявление из базы не удаляется, меняется только статус на "Удалено"
     * @param advertisementId Идентификатор объявления
     */
    public void deleteById(Integer advertisementId) {



    }

}