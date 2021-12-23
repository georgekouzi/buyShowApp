package com.example.buyshow.Model;

public class message {
    private String phone_id,title,newMessage,pid,from;
    private int type;

    message(){

    }

    public message(int type,String phone_id, String title, String newMessage, String pid,String from) {
        this.type = type;
        this.phone_id = phone_id;
        this.title = title;
        this.newMessage = newMessage;
        this.pid = pid;
        this.from=from;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }



    public String getPhone_id() {
        return phone_id;
    }

    public void setPhone_id(String phone_id) {
        this.phone_id = phone_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNewMessage() {
        return newMessage;
    }

    public void setNewMessage(String newMessage) {
        this.newMessage = newMessage;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
