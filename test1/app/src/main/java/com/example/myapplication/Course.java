package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Course implements Serializable{

    @SerializedName("id")
    @Expose
    private String courseid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("openDate")
    @Expose
    private String openDate;
    @SerializedName("level")
    @Expose
    private String level;

    private String content;

    public Course(String courseid, String coursename, String code, String description, String price, String openDate, String level) {
        this.courseid = courseid;
        this.name = coursename;
        this.code=code;
        this.description = description;
        this.price=price;
        this.openDate = openDate;
        this.level = level;
    }

    public Course(String name){
        this.name = name;
    }

    public void setCourseid(String courseid) {
        this.courseid = courseid;
    }
    public String getCourseid() {
        return courseid;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    public String getDescription() { return description; }

    public void setCode(String code) {
        this.code = code;
    }
    public String getCode() { return code; }

    public void setCoursename(String coursename) {
        this.name = coursename;
    }
    public String getCoursename() {
        return name;
    }

    public void setPrice(String price) {
        this.price = price;
    }
    public String getPrice() {
        return price;
    }

    public void setRemindtime(String openDate) {
        this.openDate = openDate;
    }
    public String getRemindtime() {
        return openDate;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public String getContent() {
        return content;
    }

    public void setTime(String level) {
        this.level = level;
    }
    public String getTime() {
        return level;
    }

}