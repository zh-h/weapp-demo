package me.zonghua.weapp.weappdemo.response;

import org.springframework.http.HttpStatus;

public class Response<T> {
    private Integer code;
    private String message;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
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

    public static Response success(Object data) {
        Response response = new Response();
        response.setCode(0);
        response.setMessage(HttpStatus.OK.name());
        response.setData(data);
        return response;
    }

    @SuppressWarnings("unChecked")
    public static Response fail(Integer code, String message, Object data) {
        Response response = new Response();
        response.setCode(code);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
