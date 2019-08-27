package com.neomu.neomu.app.search;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class PhotoFragment extends SearchFragment {


    public PhotoFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {


        Query compPostsQuery = databaseReference.child("posts").orderByChild("category").equalTo("사진");

        return compPostsQuery;

    }
}