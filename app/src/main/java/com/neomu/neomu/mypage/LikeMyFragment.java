package com.neomu.neomu.mypage;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.neomu.neomu.club.MainFragment;

public class LikeMyFragment extends MyMainFragment{

    public LikeMyFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        String myUserId = getUid();
        Query myLikePostsQuery = databaseReference.child("posts")
                .orderByChild("likeCount").limitToLast(1);

        return myLikePostsQuery;
    }
}