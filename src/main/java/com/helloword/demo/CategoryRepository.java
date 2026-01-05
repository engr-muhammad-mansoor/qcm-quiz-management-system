package com.helloword.demo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Integer> {
    public Long countById(Integer id);

    Page<Category> findAll(Pageable pageable);
}
