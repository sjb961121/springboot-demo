package springboot.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import springboot.demo.dto.NotificationDTO;
import springboot.demo.enums.NotificationEnum;
import springboot.demo.model.User;
import springboot.demo.service.NotificationService;

import javax.servlet.http.HttpServletRequest;

@Controller
public class NotificationController {
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/notification/{id}")
    public String getnotification(@PathVariable("id") Long id, HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            return "redirect:/";
        }
        NotificationDTO notificationDTO=notificationService.read(user,id);
        if (notificationDTO.getType()== NotificationEnum.REPLY_COMMENT.getType()||notificationDTO.getType()== NotificationEnum.REPLY_QUESTION.getType()){
            return "redirect:/question/"+notificationDTO.getOuterId();
        }
        else {
            return "redirect:/";
        }
    }
}
