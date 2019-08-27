package com.neomu.neomu.app.search;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class PetFragment extends SearchFragment {


    public PetFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {


        Query compPostsQuery = databaseReference.child("posts").orderByChild("category").equalTo("반려동물");

        return compPostsQuery;

    }
}