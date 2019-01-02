package com.neomu.neomu.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class User {

    public String nickName;
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

}
