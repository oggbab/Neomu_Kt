package com.neomu.neomu.app.models;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Post {

    public String uid;
    public String author;

    public String title;
    public String body;
    public String location;
    public String category;
    public String bg_img;
    public String price;
    public String date;
    public String time;

    public long likeCount = 0;
    public Map<String, Boolean> like = new HashMap<>();

    public Post() {
    }

    public Post(String uid, String author, String title, String body, String price, String category, String location, String date, String time) {
        this.uid = uid;
        this.author = author;
        this.title = title;
        this.body = body;
        this.price = price;
        this.category = category;
        this.location = location;
        this.date = date;
        this.time = time;
/*
        this.bg_img = bg_img;*/
    }

    // 맵형태로 넣기
    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("body", body);
        result.put("uid", uid);
        result.put("author", author);
        result.put("likeCount", likeCount);
        result.put("like", like);
        result.put("price", price);
        result.put("category", category);
        result.put("location", location);
        result.put("date", date);
        result.put("time", time);
/*
        result.put("bg_img", bg_img);*/

        return result;
    }

}
