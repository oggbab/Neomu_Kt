package com.neomu.neomu.app.search;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class MeetFragment extends SearchFragment {


    public MeetFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {


        Query compPostsQuery = databaseReference.child("posts").orderByChild("category").equalTo("사교");

        return compPostsQuery;

    }
}