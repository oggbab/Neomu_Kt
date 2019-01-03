package com.neomu.neomu.mypage;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.neomu.neomu.club.MainFragment;

public class LikeMyFragment extends MyMainFragment{

    public LikeMyFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

<<<<<<< HEAD
        Query myLikePostsQuery = databaseReference.child("posts")
                .orderByChild("likeCount").startAt(1);
=======
        String myUserId = getUid();
        Query myTopPostsQuery = databaseReference.child("user-posts").child(myUserId)
                .orderByChild("starCount");
>>>>>>> parent of cbe0f03... 업데이트

        return myTopPostsQuery;
    }
}