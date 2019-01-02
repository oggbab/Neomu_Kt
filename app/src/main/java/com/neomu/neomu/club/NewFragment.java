package com.neomu.neomu.club;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.neomu.neomu.mypage.MyMainFragment;


public class NewFragment extends MainFragment {


    public NewFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        Query recentPostsQuery = databaseReference.child("posts")
                .limitToLast(5);

        return recentPostsQuery;
    }
}
