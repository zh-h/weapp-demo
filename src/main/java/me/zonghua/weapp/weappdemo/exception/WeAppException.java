package me.zonghua.weapp.weappdemo.exception;

public class WeAppException extends RuntimeException {

    private Integer errorCode;


    public WeAppException(Integer errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public WeAppException(String message, Throwable cause) {
        super(message, cause);
    }

    @Override
    public String getMessage() {
        return String.format("errorCode: %s, %s", errorCode, super.getMessage());
    }

    public Integer getErrorCode() {
        return errorCode;
    }
}
