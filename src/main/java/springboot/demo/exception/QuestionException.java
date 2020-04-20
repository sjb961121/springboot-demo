package springboot.demo.exception;

public class QuestionException extends RuntimeException{
    public QuestionException(){
        super("问题不存在，请换个问题试试");
    }
}
