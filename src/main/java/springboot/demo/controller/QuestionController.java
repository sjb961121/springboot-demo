package springboot.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springboot.demo.dto.CommentDTO;
import springboot.demo.dto.QuestionDTO;
import springboot.demo.service.CommentService;
import springboot.demo.service.QuestionService;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @Autowired
    private CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Long id, Model model){
        QuestionDTO questionDTO=questionService.getQuestionById(id);
        List<CommentDTO> commentDTO=commentService.getCommentByQuestionId(id);
        questionService.incView(id);
        model.addAttribute("question",questionDTO);
        model.addAttribute("comments",commentDTO);
        return "question";
    }
}
