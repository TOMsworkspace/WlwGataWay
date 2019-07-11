package com.example.wlwgateway.message;

public class LinkEvent {
    private String message;

    public  LinkEvent(String message){
        this.message=message;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
