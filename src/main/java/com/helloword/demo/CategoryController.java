package com.helloword.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class CategoryController {

        @Autowired
        private CategoryService service;


       /* public String showCategoryList(Model model) {
            List<Category> listCategory = service.listAll();
            model.addAttribute("listCategory", listCategory);

            return "category";*/
        @GetMapping("/Category")
        public String showCategoryList(Model model, @RequestParam(defaultValue = "0") int page) {
            int pageSize = 5; // Nombre d'éléments par page
            Page<Category> categoryPage = service.listAll(PageRequest.of(page, pageSize));
            model.addAttribute("categoryPage", categoryPage);

            int totalPages = categoryPage.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(0, totalPages - 1)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }

            return "category";
        }

    @GetMapping("/Category/new")
    public String showNewForm(Model model) {
        model.addAttribute("category", new Category());
        model.addAttribute("pageTitle", "Add New Category");
        return "category_form";
    }
    @PostMapping("/Category/save")
    public String saveUser(Category category, RedirectAttributes ra) {
        service.save(category);
        ra.addFlashAttribute("message", "The category has been saved successfully.");
        return "redirect:/Category";
    }

    @GetMapping("/Category/edit/{id}")
    public String showEditForm(@PathVariable("id") Integer id, Model model, RedirectAttributes ra) {
        try {
            Category category = service.get(id);
            model.addAttribute("category", category);
            model.addAttribute("pageTitle", "Edit Category (ID: " + id + ")");

            return "category_form";
        } catch (CategoryNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
            return "redirect:/Category";
        }
    }
    @GetMapping("/Category/delete/{id}")
    public String deleteCategory(@PathVariable("id") Integer id, RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message", "The category ID " + id + " has been deleted.");
        } catch (CategoryNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/Category";
    }

}
