package com.neomu.neomu.app.club;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class NearFragment extends MainFragment {


    public NearFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        Query recentPostsQuery = databaseReference.child("posts")
                .limitToFirst(10);

        return recentPostsQuery;
    }
}