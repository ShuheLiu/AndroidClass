package com.example.myapplication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class material implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("courseId")
    @Expose
    private String courseId;
    @SerializedName("mediatype")
    @Expose
    private String mediatype;
    @SerializedName("materialType")
    @Expose
    private String materialType;
    @SerializedName("materialUrl")
    @Expose
    private String materialUrl;
    @SerializedName("createDate")
    @Expose
    private String createDate;
    @SerializedName("description")
    @Expose
    private String description;

    public String getId(){return this.id;}
    public String getCourseId(){return this.courseId;}

    public String getMediatype() {
        return mediatype;
    }

    public String getMaterialType() {
        return materialType;
    }

    public String getMaterialUrl() {
        return materialUrl;
    }

    public String getCreateDate() {
        return createDate;
    }

    public String getDescription() {
        return description;
    }
}
