package exceptions;

/*
 * @author Nina
 */
public class ExceptionDTO {

    private int code;
    private String message;

    public ExceptionDTO(int code, String description) {
        this.code = code;
        this.message = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ExceptionDTO{" + "code=" + code + ", message=" + message + '}';
    }
}
