package com.neomu.neomu.club;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.neomu.neomu.mypage.MyMainFragment;

public class PopularFragment extends MainFragment {

    public PopularFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {


        Query popularPostsQuery = databaseReference.child("posts")
                .orderByChild("likeCount").startAt(1);

        return popularPostsQuery;

    }
}