package com.neomu.neomu.club;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class PopularFragment extends MainFragment {

    public PopularFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

<<<<<<< HEAD
        Query popularPostsQuery = databaseReference.child("posts")
                .orderByChild("likeCount").startAt(1);

        return popularPostsQuery;
=======
        Query recentPostsQuery = databaseReference.child("posts")
                .limitToFirst(100);

        return recentPostsQuery;
>>>>>>> parent of cbe0f03... 업데이트

    }
}