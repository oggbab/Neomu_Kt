package com.neomu.neomu.club;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.neomu.neomu.mypage.MyMainFragment;


public class NewFragment extends MainFragment {


    public NewFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {


        String myUserId = getUid();
        Query recentPostsQuery = databaseReference.child("user-posts")
                .child(myUserId).limitToLast(3);

        return recentPostsQuery;
    }
}
