package com.neomu.neomu.mypage;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.neomu.neomu.club.MainFragment;


public class CompMyFragment extends MyMainFragment {


    public CompMyFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        String myUserId = getUid();
        Query compPostsQuery = databaseReference.child("posts").orderByChild("category").equalTo("게임");


        return compPostsQuery;
    }
}
