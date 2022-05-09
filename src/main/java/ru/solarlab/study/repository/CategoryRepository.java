package ru.solarlab.study.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.solarlab.study.entity.Category;

import java.util.Optional;

/**
 * Репозиторий категорий
 */
@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    /**
     * Возвращает категории с активным статусом
     */
    @Query("SELECT c FROM Category c WHERE c.status = 0")
    /* Прикрепляет связанные объекты */
    Page<Category> findActive(
            Pageable pageable);

    /**
     * Возвращает категорию, если у неё активный статус
     * @param id Идентификатор тага
     * @return Сущность категории
     */
    @Query("SELECT c FROM Category c WHERE c.id = (:id) AND c.status = 0")
    /* Прикрепляет связанные объекты */
    Optional<Category> findActiveById(@Param("id") Long id);
    /* Optional<> нужен для orElseThrow */

}