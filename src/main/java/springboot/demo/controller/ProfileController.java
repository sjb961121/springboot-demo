package springboot.demo.controller;

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
    private UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    @GetMapping("/question")
    public String myquestion(Model model, HttpServletRequest request,
                             @RequestParam("page")Integer page,
                             @RequestParam("size")Integer size){
        Cookie[] cookies = request.getCookies();
        User user=null;
        if (cookies!=null && cookies.length!=0)
            for (Cookie cookie:cookies){
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user = userMapper.selectBytoken(token);
                    if (user!=null){
                        request.getSession().setAttribute("user",user);
                    }
                    break;
                }
            }
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "redirect:/";
        }
        List<QuestionDTO> list=questionService.listByUserId(page,size,user.getId(),model);
        model.addAttribute("questions",list);
        model.addAttribute("section","question");
        model.addAttribute("sectionName","我的问题");
        return "profile";
    }

    @GetMapping("/reply")
    public String myreply(Model model){
        model.addAttribute("section","reply");
        model.addAttribute("sectionName","新的回复");
        return "profile";
    }
}
