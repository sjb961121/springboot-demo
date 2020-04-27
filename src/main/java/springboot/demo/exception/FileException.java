package springboot.demo.exception;

public class FileException extends RuntimeException{
    private Integer code;
    public FileException(String message,Integer code) {
        super(message);
        this.code=code;
    }

    public Integer getCode() {
        return code;
    }
}
