package ru.solarlab.study.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.solarlab.study.entity.Category;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {

    //List<Category> findById(Integer id);

}