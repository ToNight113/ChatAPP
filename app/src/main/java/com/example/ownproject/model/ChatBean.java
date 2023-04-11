package com.example.ownproject.model;
public class ChatBean {
    //发送消息
    public static final int SEND=0;
    //接收消息
    public static final int RECEIVE=1;
    //消息的状态（是接收还是发送）
    private int state;
    //消息的内容
    private String message;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
