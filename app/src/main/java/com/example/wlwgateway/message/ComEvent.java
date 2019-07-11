package com.example.wlwgateway.message;

public class ComEvent {
    private String message;

    public  ComEvent(String message){
        this.message=message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
