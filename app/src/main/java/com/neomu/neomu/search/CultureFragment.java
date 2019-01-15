package com.neomu.neomu.search;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class CultureFragment extends SearchFragment {


    public CultureFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {


        Query compPostsQuery = databaseReference.child("posts").orderByChild("category").equalTo("문화공연");

        return compPostsQuery;

    }
}