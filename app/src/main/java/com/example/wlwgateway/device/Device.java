package com.example.wlwgateway.device;

public class Device {
    private String name;
    private String no;
    private String state;
    private String pic;

    public Device(String name, String no, String state, String pic) {
        super();
        this.name = name;
        this.no = no;
        this.state = state;
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
