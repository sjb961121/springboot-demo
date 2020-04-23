package springboot.demo.exception;

public class CommentException extends RuntimeException {
    private Integer code;
    public CommentException(String message,Integer code) {
        super(message);
        this.code=code;
    }

    public Integer getCode() {
        return code;
    }
}
