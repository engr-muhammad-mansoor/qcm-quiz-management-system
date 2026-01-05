package com.helloword.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository repo;
    @Autowired
    private CategoryRepository categorieRepository;

    public List<Question> listAll() {
        return (List<Question>) repo.findAll();
    }

    public void save(Question question) {
        repo.save(question);
    }

    public List<Category> getAllCategory() {
        return (List<Category>) categorieRepository.findAll();
    }

    public Question get(Integer id) {
        Optional<Question> result = repo.findById(id);
        if (result.isPresent()) {
            return result.get();
        }

        return null;
    }

    public void delete(Integer id) {
        Long count = repo.countById(id);
        if (count == null || count == 0) {

        }
        repo.deleteById(id);
    }
    public Page<Question> listAll(Pageable pageable) {
        return repo.findAll(pageable);
    }


    public List<Question> searchByCategoryName(String categoryName) {
        return repo.findByCategoryNameContainingIgnoreCase(categoryName);
    }

    public int searchByCategory(Category category) {
//        List<Question> questions = repo.findAllByCategory(category);
//        return questions.size();
        return repo.countByCategory(category);
    }
    
    /*
    public List<Question> searchQuestion(Long categoryId, String keyword) {
        return repo.findByCategoryNameContainingIgnoreCase(categoryName);
    }*/
}