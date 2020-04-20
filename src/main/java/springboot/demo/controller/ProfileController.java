package springboot.demo.controller;

import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.demo.dto.QuestionDTO;
import springboot.demo.mapper.UserMapper;
import springboot.demo.model.Question;
import springboot.demo.model.User;
import springboot.demo.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {


    @Autowired
    private QuestionService questionService;
    @GetMapping("/question")
    public String myquestion(Model model, HttpServletRequest request,
                             @RequestParam(value = "page",defaultValue = "1")Integer page,
                             @RequestParam(value = "size",defaultValue = "5")Integer size){
         User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "redirect:/";
        }
        PageInfo pageInfo =questionService.listByUserId(page,size,user.getId());
        model.addAttribute("pageInfo",pageInfo);
        List list = questionService.toQuestionDTO(pageInfo.getList());
        model.addAttribute("questions",list);
        model.addAttribute("section","question");
        model.addAttribute("sectionName","我的问题");
        return "profile";
    }

    @GetMapping("/reply")
    public String myreply(Model model,HttpServletRequest request,
                          @RequestParam(value = "page",defaultValue = "1")Integer page,
                          @RequestParam(value = "size",defaultValue = "5")Integer size){
        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "redirect:/";
        }
        PageInfo pageInfo =questionService.listByUserId(page,size,user.getId());
        model.addAttribute("pageInfo",pageInfo);
        List list = questionService.toQuestionDTO(pageInfo.getList());
        model.addAttribute("questions",list);
        model.addAttribute("section","reply");
        model.addAttribute("sectionName","新的回复");
        return "profile";
    }
}
