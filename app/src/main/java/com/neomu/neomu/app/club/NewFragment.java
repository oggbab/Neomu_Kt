package com.neomu.neomu.app.club;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;


public class NewFragment extends MainFragment {


    public NewFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {


        String myUserId = getUid();
        Query recentPostsQuery = databaseReference.child("user-posts")
                .child(myUserId).limitToLast(3);

        return recentPostsQuery;
    }
}
