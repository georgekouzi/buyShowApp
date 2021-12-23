package com.example.buyshow.Model;

public class Cart {
    private String pid,pname , price,quantity,discounnt;
    public Cart(){

    }
    public Cart(String pid, String pname , String price, String quantity, String discounnt){
        this.pid = pid;
        this.pname = pname;
        this.price = price;
        this.price = quantity = quantity;
        this.discounnt = discounnt;

    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDiscounnt() {
        return discounnt;
    }

    public void setDiscounnt(String discounnt) {
        this.discounnt = discounnt;
    }


}
