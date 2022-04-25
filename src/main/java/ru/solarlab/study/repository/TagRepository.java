package ru.solarlab.study.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import ru.solarlab.study.entity.Tag;

@Repository
public interface TagRepository extends PagingAndSortingRepository<Tag, Integer> {

    //List<Category> findById(Integer id);

}