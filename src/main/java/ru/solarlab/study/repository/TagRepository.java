package ru.solarlab.study.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.solarlab.study.entity.Tag;

import java.util.Optional;

/**
 * Репозиторий тагов
 */
@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Long> {

    /**
     * Возвращает таг с прикрепленными объявлениями
     * @param id Идентификатор тага
     * @return Сущность тага
     */
    @Query("SELECT t FROM Tag t JOIN FETCH t.advertisements WHERE t.id = (:id)")
    /* Прикрепляет связанные объекты */
    Optional<Tag> findByIdAndFetchAdvertisements(@Param("id") Long id);
    /* Optional<> нужен для orElseThrow */

}