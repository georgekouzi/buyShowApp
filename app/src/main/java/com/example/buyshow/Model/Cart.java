package com.example.buyshow.Model;

public class Cart {
    private String date,time,pidOrder,BuyerID, pid, pname ,SellerID, price,quantity,buyerIDOrders;
    public Cart(){

    }
    public Cart(String time,String date,String pidOrder ,String BuyerID,String SellerID,String pid, String pname , String price, String quantity, String buyerIDOrders){
        this.pid = pid;
        this.pidOrder=pidOrder;
        this.pname = pname;
        this.price = price;
        this.quantity = quantity;
        this.buyerIDOrders = buyerIDOrders;
        this.SellerID=SellerID;
        this.BuyerID=BuyerID;
        this.date=date;
        this.time=time;


    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }


    public String getTime() {
        return time;
    }
    public void setTime(String date) {
        this.time = time;
    }






    public String getPidOrder() {
        return pidOrder;
    }

    public void setPidOrder(String pidOrder) {
        this.pidOrder = pidOrder;
    }



    public String getBuyerID() {
        return BuyerID;
    }
    public void setBuyerID(String BuyerID) {
        this.BuyerID = BuyerID;
    }

    public String getSellerID() {
        return SellerID;
    }
    public void setSellerID(String SellerID) {
        this.SellerID = SellerID;
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

    public String getBuyerIDOrders() {
        return buyerIDOrders;
    }

    public void setBuyerIDOrders(String buyerIDOrders) {
        this.buyerIDOrders = buyerIDOrders;
    }


}
