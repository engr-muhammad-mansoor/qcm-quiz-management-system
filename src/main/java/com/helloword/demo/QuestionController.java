package com.helloword.demo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//@RestController
//@RequestMapping("/questions")
@Controller
public class QuestionController {

    @Autowired
    private QuestionService service;
    
    @Autowired
    private EntityManager entityManager;

  /*  @GetMapping("/Question")
    public String showQuestionList(Model model) {
        List<Question> listQuestion = service.listAll();
        model.addAttribute("listQuestion", listQuestion);

        return "question";
    }
*/
  @GetMapping("/Question")
  public String showQuestionList(Model model, @RequestParam(defaultValue = "0") int page) {
      int pageSize = 5; // Nombre d'éléments par page
      Page<Question> questionPage = service.listAll(PageRequest.of(page, pageSize));
      model.addAttribute("questionPage", questionPage);

      int totalPages = questionPage.getTotalPages();
      if (totalPages > 0) {
          List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1)
                  .boxed()
                  .collect(Collectors.toList());
          model.addAttribute("pageNumbers", pageNumbers);
      }
      
      model.addAttribute("categories", service.getAllCategory());
      System.out.println(model);

      return "question";
  }
    @GetMapping("/Question/new")
    public String showNewForm(Model model) {
        model.addAttribute("question", new Question());
        model.addAttribute("categories", service.getAllCategory());
        model.addAttribute("pageTitle", "Add New Question");
        return "question_form";
    }
    @PostMapping("/Question/save")
    public String saveUser(Question question, RedirectAttributes ra) {

        service.save(question);
        ra.addFlashAttribute("message", "The question has been saved successfully.");
        return "redirect:/Question";
    }



    /*
    @GetMapping("/Question/new")
    public String showNewForm(Model model) {
        Question question = new Question();
        List<Category> categories = service.getAllCategory();
        model.addAttribute("question", question);
        model.addAttribute("categories", categories);
        return "question_form"; // Nom du template Thymeleaf
    }

    @PostMapping("/Question/save")
    public Question save(@ModelAttribute("question") Question question) {
        return service.save(question);
    }*/

    @GetMapping("/Question/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {

            Question question = service.get(id);
            model.addAttribute("question", question);
        model.addAttribute("categories", service.getAllCategory());
            model.addAttribute("pageTitle", "Edit Category (ID: " + id + ")");

            return "question_form";

    }
    @GetMapping("/Question/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id, RedirectAttributes ra) {

            service.delete(id);
            ra.addFlashAttribute("message", "The category ID " + id + " has been deleted.");

        return "redirect:/Question";
    }

    
    @GetMapping("/Question/search")
    public String searchQuestions(@RequestParam String categoryId, @RequestParam String keyword, Model model) {
        model.addAttribute("categories", service.getAllCategory());
        TypedQuery<Question> query = entityManager.createQuery(
                "SELECT q FROM Question q WHERE (:categoryId is null or q.category.id = :categoryId) and (:keyword is null or q.name like :keyword)" , Question.class);
        Integer vCategoryId = (categoryId.equals("''") || categoryId.equals("") ||  categoryId == null ) ? null : Integer.valueOf(categoryId);
        String vKeyword = (keyword == "" ||  keyword == null ) ? null : "%" + keyword + "%";
        List<Question> questions = query.setParameter("categoryId", vCategoryId).setParameter("keyword", vKeyword).getResultList();
        System.out.println(questions);
        model.addAttribute("questions", questions);
        return "search";
    }
}