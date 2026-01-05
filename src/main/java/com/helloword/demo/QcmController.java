package com.helloword.demo;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class QcmController {
    @Autowired
    private PDFGenerator pdfGenerator;

    @Autowired
    private QuestionService service;
    @Autowired
    private CategoryRepository categoryRepository;


    @GetMapping("/Qcm")
    public String sQcmController(Model model) {
        model.addAttribute("categories", service.getAllCategory());
        List<Category> categories = (List<Category>) categoryRepository.findAll(); // Récupérez la liste des catégories
        Map<Long,Integer> questionCount = new HashMap<>();
        for(Category category : categories){
            Integer count = service.searchByCategory(category);
            questionCount.put(category.getId(),count);
        }
        model.addAttribute("questionCountForCategory", questionCount);
        model.addAttribute("categories", categories); // Assuming you also want to display questions
        return "qcm"; // Assuming "questions" is the name of your HTML template
    }



    @PostMapping("/QcmTest")
    public ResponseEntity<?> generateMultiplePDFs(HttpServletRequest request, RedirectAttributes ra) {
        try {
            int numberOfTests = Integer.parseInt(request.getParameter("numberOfTests"));
            int numberOfQuestions = Integer.parseInt(request.getParameter("numberOfQuestions"));

            // Convert the questionData map back to a Map<Long, Integer>
            Map<Long, Integer> questionData = new HashMap<>();
            for (String key : request.getParameterMap().keySet()) {
                if (key.startsWith("questionData[")) {
                    Long categoryId = Long.parseLong(key.substring("questionData[".length(), key.length() - 1));
                    Integer count = Integer.parseInt(request.getParameter(key));
                    questionData.put(categoryId, count);
                }
            }
            byte[] zipContent = pdfGenerator.generatePDFs(numberOfTests, numberOfQuestions, questionData);
            return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=invoices.zip").body(zipContent);
        } catch (IllegalArgumentException | IOException e) {
            // Return the error message as a JSON object
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }
}
