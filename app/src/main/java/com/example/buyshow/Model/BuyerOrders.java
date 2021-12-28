package com.example.buyshow.Model;

public class BuyerOrders {
    private String BuyerID,state,country,pidOrder,name ,phone ,address ,city , date , time, totalAmount;

    public BuyerOrders() {
    }

    public BuyerOrders(String BuyerID,String country ,String pidOrder, String name, String phone, String address, String city, String state, String date, String time, String totalAmount) {
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.state = state;
        this.date = date;
        this.time = time;
        this.country=country;
        this.totalAmount = totalAmount;
        this.pidOrder=pidOrder;
        this.BuyerID=BuyerID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }


    public String getBuyerID() {
        return BuyerID;
    }

    public void setBuyerID(String BuyerID) {
        this.BuyerID = BuyerID;
    }

    public String getPidOrder() {
        return pidOrder;
    }

    public void setPidOrder(String pidOrder) {
        this.pidOrder = pidOrder;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
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

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }
}
