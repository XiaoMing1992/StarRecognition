package com.konka.kkstar.bean;

/**
 * Created by Zhou Weilin on 2018-5-4.
 */

public class ResponseBean<T> {

    /**
     * code : 200
     * message : success
     * data : {"id":1,"token":"6659aa9f0db7f5af721a7849f6d15396","expireTime":1526544287696}
     */

    private int code;
    private String message;
    private T data;

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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
