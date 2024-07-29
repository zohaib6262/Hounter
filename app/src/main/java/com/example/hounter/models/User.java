package com.example.hounter.models;

public class User {

    private int image;
    private String username;
    private String password;
    private String contact;
    private String userId;

    // No-argument constructor
    public User() {
        this.username = "";
        this.password = "";
        this.contact = "";
        this.userId = "";
        this.image = 0;
    }

    // Parameterized constructor
    public User(String username, String password, String contact, String userId, int image) {
        this.username = username;
        this.password = password;
        this.contact = contact;
        this.userId = userId;
        this.image = image;

    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    // Getter and setter for username
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    // Getter and setter for password
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Getter and setter for contact
    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    // Getter and setter for userId
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", contact='" + contact + '\'' +
                ", userId='" + userId + '\'' +
                '}';
    }
}

