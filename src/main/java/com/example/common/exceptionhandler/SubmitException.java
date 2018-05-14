package com.example.common.exceptionhandler;

//!!!!!!注意，若是修改包名，编译后的路径包名可能会出现不更改的问题，就会导致ClassNotFound的错误!!!!!!!
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
