package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable{

    @SerializedName("id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("level")
    @Expose
    private String level;

    public User(String username, String password){
        this.name = username;
        this.password = password;
        level = "MEMBER";
        userId = "123";
        email = "ddd";
        mobile = "88888888888";
    }
    public User(String name){
        this.name = name;
    }

    public void setUserId(String id) {
        this.userId = id;
    }
    public String getUserId() {
        return userId;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public User(String userId, String name, String email, String password, String mobile, String level) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.mobile = mobile;
        this.level = level;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return password;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getMobile() {
        return mobile;
    }



    public String getRoal() {
        return level;
    }

    public void setRoal(String level){
        this.level = level;
    }
}
