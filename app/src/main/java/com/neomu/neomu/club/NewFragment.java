package com.neomu.neomu.club;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;


public class NewFragment extends MainFragment {


    public NewFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        Query recentPostsQuery = databaseReference.child("posts")
                .limitToFirst(100);

        return recentPostsQuery;
    }
}
