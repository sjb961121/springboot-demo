package springboot.demo.exception;


public class NotificationException extends RuntimeException {
    private Integer code;
    public NotificationException(String message, Integer code) {
        super(message);
        this.code=code;
    }

    public Integer getCode() {
        return code;
    }
}
