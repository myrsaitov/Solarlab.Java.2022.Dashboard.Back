package ru.solarlab.study.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
     * Возвращает таг, если у него активный статус
     * @param id Идентификатор тага
     * @return Сущность тага
     */
    @Query("SELECT t FROM Tag t WHERE t.id = (:id) AND t.status = 0")
    /* Прикрепляет связанные объекты */
    Optional<Tag> findActiveById(@Param("id") Long id);
    /* Optional<> нужен для orElseThrow */

    /**
     * Возвращает таг с любым статусом и прикрепленными объявлениями
     * @param id Идентификатор тага
     * @return Сущность тага
     */
    @Query("SELECT t FROM Tag t JOIN FETCH t.advertisements WHERE t.id = (:id)")
        /* Прикрепляет связанные объекты */
    Optional<Tag> findByIdAndFetchAdvertisements(@Param("id") Long id);
    /* Optional<> нужен для orElseThrow */

    /**
     * Возвращает таги с активным статусом
     */
    @Query("SELECT t FROM Tag t WHERE t.status = 0")
    /* Прикрепляет связанные объекты */
    Page<Tag> findActive(
            Pageable pageable);

}