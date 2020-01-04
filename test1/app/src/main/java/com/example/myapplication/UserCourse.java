package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserCourse implements Serializable {
    @SerializedName("user_id")
    @Expose
    private String user_id;
    @SerializedName("course_id")
    @Expose
    private String course_id;
    @SerializedName("attendtime")
    @Expose
    private String attendtime;

    public UserCourse(String user_id,String course_id){
        this.user_id=user_id;
        this.course_id=course_id;
        this.attendtime="2019";
    }

    public String getUser_id() {
        return user_id;
    }
    public String getCourse_id() {
        return course_id;
    }
}
