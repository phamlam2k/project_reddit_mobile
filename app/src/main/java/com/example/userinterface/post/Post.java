package com.example.userinterface.post;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Post {
    private int like, unlike;
    private String name, desc, time;
    public Post(){}

    public Post(int like, int unlike, String name, String desc, String time) {
        this.like = like;
        this.unlike = unlike;
        this.name = name;
        this.desc = desc;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public int getUnlike() {
        return unlike;
    }

    public void setUnlike(int unlike) {
        this.unlike = unlike;
    }

    @Override
    public String toString() {
        return "Post{" +
                "like=" + like +
                ", unlike=" + unlike +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public Map<String , Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("desc", desc);

        return result;
    }

    public Map<String , Object> toMapLike(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("like", like);

        return result;
    }

    public Map<String , Object> toMapUnLike(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("unlike", unlike);

        return result;
    }
}
