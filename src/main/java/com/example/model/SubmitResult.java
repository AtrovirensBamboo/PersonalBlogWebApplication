package com.example.model;


public class SubmitResult {
    //状态信息，成功：success，失败：failed
    private String status;
    //错误信息
    private String message;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

}
