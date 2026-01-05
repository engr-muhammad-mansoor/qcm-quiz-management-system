package com.helloword.demo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestionRepository extends CrudRepository<Question, Integer> {
    public Long countById(Integer id);

    Page<Question> findAll(Pageable pageable);



    @Query("SELECT q FROM Question q WHERE LOWER(q.category.name) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Question> findByCategoryNameContainingIgnoreCase(@Param("keyword") String keyword);

    List<Question> findAllByCategory(Category category);

    @Query("SELECT COUNT(*) FROM Question WHERE category = :category")
    int countByCategory(Category category);
}


