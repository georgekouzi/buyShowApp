package com.example.buyshow.Model;

public class User {

    private  String name, phone ,password;
    private int messageN,rank,sellCounter;

    public User(){

    }

    public User(int messageN,String name, String phone, String password,int rank,int sellCounter) {
        this.messageN = messageN;
        this.name = name;
        this.phone = phone;
        this.password = password;
        this.rank =rank;
        this.sellCounter=sellCounter;
    }

    public int getSellCounter() {
        return sellCounter;
    }

    public void setSellCounter(int sellCounter) {
        this.sellCounter = sellCounter;
    }


    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getMessageN() {
        return messageN;
    }

    public void setMessageN(int messageN) {
        this.messageN = messageN;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
