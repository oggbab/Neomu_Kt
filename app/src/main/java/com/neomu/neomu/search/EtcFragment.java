package com.neomu.neomu.search;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

public class EtcFragment extends SearchFragment {


    public EtcFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {


        Query compPostsQuery = databaseReference.child("posts").orderByChild("category").equalTo("기타");

        return compPostsQuery;

    }
}