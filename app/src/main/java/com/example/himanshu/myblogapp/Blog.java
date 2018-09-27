package com.example.himanshu.myblogapp;

public class Blog {
    private String Title;
    private String Desc;
    private String Image;
    private String Time;
    private String UserId;

    public Blog() {
    }

    public Blog(String title, String desc, String image, String time, String userId) {
        Title = title;
        Desc = desc;
        Image = image;
        Time = time;
        UserId = userId;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }
}