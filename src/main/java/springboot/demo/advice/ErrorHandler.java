package springboot.demo.advice;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import springboot.demo.exception.QuestionException;

@ControllerAdvice
public class ErrorHandler {
        @ExceptionHandler(QuestionException.class)
        public String handlerexception(Throwable e,Model model){
            model.addAttribute("errormessage",e.getMessage());
            return "error";
        }
}
