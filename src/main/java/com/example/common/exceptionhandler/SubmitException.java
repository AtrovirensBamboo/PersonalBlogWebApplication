package com.example.common.exceptionhandler;

//自定义异常
public class SubmitException extends Exception {
    private static final long serialVersionUID = 1L;
    private String message;

    public SubmitException(String message){
        super(message);
        this.message = message;
    }
    @Override
    public String getMessage() {
        return message;
    }
}
