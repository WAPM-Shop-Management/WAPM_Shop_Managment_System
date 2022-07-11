package spring.util;

/**
 * Created by Sahan Nimesha on 2022 - Jul
 * In IntelliJ IDEA
 */
public class StandardResponse {
    private int code;
    private String message;
    private Object data;

    public StandardResponse() {
    }

    public StandardResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
