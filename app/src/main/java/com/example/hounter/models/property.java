package com.example.hounter.models;


public class property {

    String p_id, userId;
    int image_res;
    String price;
    String desc, address;
    int rooms;

    public property(int image_res, String price, String desc) {
        this.image_res = image_res;
        this.price = price;
        this.desc = desc;
    }

    public property(String p_id, String userId, int image_res, String price, String desc, String address, int rooms) {
        this.p_id = p_id;
        this.userId = userId;
        this.image_res = image_res;
        this.price = price;
        this.desc = desc;
        this.address = address;
        this.rooms = rooms;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getId() { return p_id; }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImage_res(int image_res) {
        this.image_res = image_res;
    }

    public void setId(String id) {
        this.p_id = p_id;
    }

    public int getRooms() {
        return rooms;
    }

    public String getAddress() {
        return address;
    }

    public String getDesc() {
        return desc;
    }

    public String getPrice() {
        return price;
    }

    public int getImage_res() {
        return image_res;
    }
}
