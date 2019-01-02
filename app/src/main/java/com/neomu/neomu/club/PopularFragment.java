package com.neomu.neomu.club;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class PopularFragment extends MainFragment {

    public PopularFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        Query recentPostsQuery = databaseReference.child("posts")
                .limitToFirst(100);

        return recentPostsQuery;

    }
}