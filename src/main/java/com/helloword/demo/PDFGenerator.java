package com.helloword.demo;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Component
public class PDFGenerator {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private QuestionRepository questionRepository;

    private byte[] generatePDF(int i, int numberOfQuestions, Map<Long, Integer> questionData) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try (PdfWriter writer = new PdfWriter(outputStream); PdfDocument pdfDocument = new PdfDocument(writer); Document document = new Document(pdfDocument)) {
            i = i + 1;
            addTitleText(document, "Série:  test_" + i);
            addTitleText1(document, "Name:  ");
            addTitleText1(document, "First Name:  ");
            addSubHeading(document, "Exam Rules: ");
            addRule(document, "1. All questions should be answered.");
            addRule(document, "2. All questions marks are equal (1 for each).");
            addRule(document, "3. Don't leave the exam hall before the exam ends.");
            document.add(new AreaBreak());
            addTitleText(document, "Questions starts from below:");
            addSubHeading(document, "");
            addSubHeading(document, "Total Questions : " + numberOfQuestions);
            addQuestions(document, questionData);

        }

        return outputStream.toByteArray();
    }

    private void addQuestions(Document document, Map<Long, Integer> questionData) {
        int i = 1;
        for (Map.Entry<Long, Integer> entry : questionData.entrySet()) {
            Long key = entry.getKey();
            Integer value = entry.getValue();
            if (value > 0) {
                Category category = categoryRepository.findById(Math.toIntExact(key)).orElse(null);

                if (category != null) {
                    List<Question> questions = questionRepository.findAllByCategory(category);
                    if (questions.size() < value) {
                        throw new IllegalArgumentException("Number of questions exceeds for " + category.getName());
                    } else {
                        List<Question> askedQuestions = new ArrayList<>(questions.subList(0, value));
                        Collections.shuffle(askedQuestions);
//                        int i = 1;
//                        addSubHeading(document,category.getName());
//                        int j = 1;
                        for (Question question : askedQuestions) {

                            List<String> options = Arrays.asList(question.getCha(), question.getChab(), question.getChac(), question.getChad());
                            Collections.shuffle(options);

                            Paragraph questionParagraph = new Paragraph("Q" + i + ": " + question.getName()).setFontSize(14).setMarginTop(8);
                            questionParagraph.setTextAlignment(TextAlignment.LEFT);
                            document.add(questionParagraph);

                            // Print question options
                            Paragraph optionsParagraph = new Paragraph();
                            optionsParagraph.add("A. " + options.get(0) + "\n");
                            optionsParagraph.add("B. " + options.get(1) + "\n");
                            optionsParagraph.add("C. " + options.get(2) + "\n");
                            optionsParagraph.add("D. " + options.get(3) + "\n");
                            document.add(optionsParagraph);
                            i = i + 1;
//                            j = j + 1;
                            if ((i - 6) % 6 == 0 || i == 6) {
                                document.add(new AreaBreak());
                            }
                        }
                    }
                }
            }
        }
    }

    private void addTitleText(Document document, String titleText) {
        Paragraph paragraph = new Paragraph(titleText).setBold().setFontSize(20).setMarginTop(20);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        document.add(paragraph);
    }

    private void addTitleText1(Document document, String titleText) {
        Paragraph paragraph = new Paragraph(titleText).setBold().setFontSize(14).setMarginTop(12);
        paragraph.setTextAlignment(TextAlignment.CENTER);
        document.add(paragraph);
    }

    private void addSubHeading(Document document, String titleText) {
        Paragraph paragraph = new Paragraph(titleText).setBold().setFontSize(14).setMarginTop(12);
        paragraph.setTextAlignment(TextAlignment.LEFT);
        document.add(paragraph);
    }


    private void addRule(Document document, String ruleText) {
        Paragraph paragraph = new Paragraph(ruleText).setFontSize(14).setMarginTop(8);
        paragraph.setTextAlignment(TextAlignment.LEFT);
        document.add(paragraph);
    }

    private void addInvoiceTable(Document document) {
        Table table = new Table(2);
        table.addCell("Invoice Number");

        table.addCell("Invoice Number");
        table.addCell("Client Name");
        table.addCell("Client Name");
        table.addCell("Date");
        table.addCell("Date");
        table.addCell("Details");
        /*com.itextpdf.layout.element.List list=new com.itextpdf.layout.element.List();
        invoice.getDetails().forEach(list::add);
        table.addCell(list);*/
        document.add(table);
    }


    public byte[] generatePDFs(int numberOfTests, int numberOfQuestions, Map<Long, Integer> questionData) throws IOException {
        ByteArrayOutputStream zipStream = new ByteArrayOutputStream();
        try (ZipOutputStream zipOutputStream = new ZipOutputStream(zipStream)) {
            for (int i = 0; i < numberOfTests; i++) {
                byte[] pdfContent = generatePDF(i, numberOfQuestions, questionData);
                zipOutputStream.putNextEntry(new ZipEntry("test_" + (i + 1) + ".pdf"));
                zipOutputStream.write(pdfContent);
                zipOutputStream.closeEntry();
            }
        }
        return zipStream.toByteArray();
    }
}