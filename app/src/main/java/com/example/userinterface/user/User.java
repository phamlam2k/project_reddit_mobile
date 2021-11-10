package com.example.userinterface.user;

import com.example.userinterface.post.Comment;
import com.example.userinterface.post.Post;

import java.util.ArrayList;

public class User {
    public String username, email, password;
    public Boolean isActive ;

    public User() {}

    public User(String username, String email, String password, Boolean isActive) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isActive = isActive;
    }
}
