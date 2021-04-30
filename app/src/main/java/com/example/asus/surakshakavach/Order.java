package com.example.asus.surakshakavach;

public class Order {
    String  bill;
    String flag;
    String qty;
    String time_stamp;

    public Order(String bill, String flag, String qty, String time_stamp) {
        this.bill = bill;
        this.flag = flag;
        this.qty = qty;
        this.time_stamp = time_stamp;
    }

    public String getBill() {
        return bill;
    }

    public void setBill(String bill) {
        this.bill = bill;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getTime_stamp() {
        return time_stamp;
    }

    public void setTime_stamp(String time_stamp) {
        this.time_stamp = time_stamp;
    }
}
