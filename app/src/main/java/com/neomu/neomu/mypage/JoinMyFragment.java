package com.neomu.neomu.mypage;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.neomu.neomu.club.MainFragment;

public class JoinMyFragment extends MyMainFragment {


    public JoinMyFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        Query recentPostsQuery = databaseReference.child("posts")
                .limitToFirst(1);

        return recentPostsQuery;

    }
}