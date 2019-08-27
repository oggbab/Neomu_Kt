package com.neomu.neomu.app.search;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class SportFragment extends SearchFragment {


    public SportFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {


        Query compPostsQuery = databaseReference.child("posts").orderByChild("category").equalTo("스포츠");

        return compPostsQuery;

    }
}