package springboot.demo.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import springboot.demo.exception.CommentException;
import springboot.demo.exception.QuestionException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ErrorHandler {
        @ExceptionHandler(QuestionException.class)
        public String questionhandler(QuestionException e, HttpServletRequest request){
//            model.addAttribute("errormessage",e.getMessage());
            request.setAttribute("javax.servlet.error.status_code",400);
            request.setAttribute("code",e.getCode());
            request.setAttribute("errormessage",e.getMessage());
            return "forward:/error";
        }

    @ExceptionHandler(CommentException.class)
    public String commenthandler(CommentException e, HttpServletRequest request){
//            model.addAttribute("errormessage",e.getMessage());
        request.setAttribute("javax.servlet.error.status_code",400);
        request.setAttribute("code",e.getCode());
        request.setAttribute("errormessage",e.getMessage());
        return "forward:/error";
    }
}
