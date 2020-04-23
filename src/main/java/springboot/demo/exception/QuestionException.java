package springboot.demo.exception;

public class QuestionException extends RuntimeException{
    private Integer code;

    public QuestionException(String message, Integer code) {
        super(message);
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
