package com.example.wlwgateway.message;

public class NetEvent {
    private String message;

    public  NetEvent(String message){
        this.message=message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
