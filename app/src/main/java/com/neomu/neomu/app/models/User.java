package com.neomu.neomu.app.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String username;
    public static String nickName;
    public String email;
    public String gender;
    public String birth;

    public User() {
    }

    public User(String nickName, String email,String gender, String birth) {
        this.nickName = nickName;
        this.email = email;
        this.gender = gender;
        this.birth = birth;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public static String getNickName() {return nickName;}
    public String getEmail() {return email;}
    public String getGender() {return gender;}
    public String getBirth() {return birth;}
}
