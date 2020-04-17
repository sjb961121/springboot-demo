package springboot.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springboot.demo.mapper.QuestionMapper;
import springboot.demo.mapper.UserMapper;
import springboot.demo.model.Question;
import springboot.demo.model.User;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;


    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @PostMapping("/publish")
    public String publishquestion(@RequestParam(name = "title",required = false) String title,
                                  @RequestParam(name = "tag",required = false) String tag,
                                  @RequestParam(name = "description",required = false) String description,
                                  HttpServletRequest request,
                                  Model model){
        model.addAttribute("title",title);
        model.addAttribute("tag",tag);
        model.addAttribute("description",description);

        if ((title==null)||(title=="")){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }
        if ((description==null)||(description=="")){
            model.addAttribute("error","补充不能为空");
            return "publish";
        }
        if ((tag==null)||(tag=="")){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);

        User user = (User)request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        question.setGmtCreate(System.currentTimeMillis());
        question.setCreator(user.getId());
        question.setGmtModified(question.getGmtCreate());

        questionMapper.insert(question);

        return "redirect:/";
    }
}
