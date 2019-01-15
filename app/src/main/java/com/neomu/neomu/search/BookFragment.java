package com.neomu.neomu.search;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class BookFragment extends SearchFragment {


    public BookFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {


        Query compPostsQuery = databaseReference.child("posts").orderByChild("category").equalTo("독서");

        return compPostsQuery;

    }
}