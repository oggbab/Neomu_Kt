package com.neomu.neomu.models;

import com.google.firebase.database.IgnoreExtraProperties;

// [START comment_class]
@IgnoreExtraProperties
public class Comment {

    public String uid;
    public String nickName;
    public String text;

    public Comment() {
        // Default constructor required for calls to DataSnapshot.getValue(Comment.class)
    }

    public Comment(String uid, String nickName, String text) {
        this.uid = uid+"님: ";
        this.nickName = nickName;
        this.text = text;
    }

}
// [END comment_class]
