package springboot.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springboot.demo.dto.QuestionDTO;
import springboot.demo.service.QuestionService;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, Model model){
        QuestionDTO questionDTO=questionService.getQuestionById(id);
        model.addAttribute("question",questionDTO);
        return "question";
    }
}
