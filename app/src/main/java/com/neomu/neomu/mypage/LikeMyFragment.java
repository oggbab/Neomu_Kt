package com.neomu.neomu.mypage;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.neomu.neomu.club.MainFragment;

public class LikeMyFragment extends MyMainFragment{

    public LikeMyFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        Query myLikePostsQuery = databaseReference.child("posts")
                .orderByChild("likeCount").startAt(1);

        return myLikePostsQuery;
    }
}