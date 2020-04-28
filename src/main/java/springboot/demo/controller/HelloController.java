package springboot.demo.controller;

import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.demo.dto.QuestionDTO;
import springboot.demo.mapper.QuestionMapper;
import springboot.demo.mapper.UserMapper;
import springboot.demo.model.Question;
import springboot.demo.model.User;
import springboot.demo.service.QuestionService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
public class HelloController {
//    @RequestMapping("/hello")
//    public String hello(@RequestParam(name = "name") String name, Model model){
//        model.addAttribute("name",name);
//        return "index";
//    }


    @Autowired
    private QuestionService questionService;
    @RequestMapping("/")
    public String hello(HttpServletRequest request, Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5")Integer size,
                        @RequestParam(name = "search",required = false) String search){
        User user = (User)request.getSession().getAttribute("user");

        PageInfo pageInfo=questionService.list(page,size,search);
        model.addAttribute("search",search);
        model.addAttribute("pageInfo",pageInfo);
      List<QuestionDTO> questionList=questionService.toQuestionDTO(pageInfo.getList());
          model.addAttribute("questions",questionList);
//        System.out.println(model);
        return "index";
    }

}
