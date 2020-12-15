package com.kuka.domain;


public class ResultDto<T> {
    public T data;
    public String message;
    public int code;

    public ResultDto(){}

    public ResultDto(int code, String message){
        this.code= code;
        this.message=message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
