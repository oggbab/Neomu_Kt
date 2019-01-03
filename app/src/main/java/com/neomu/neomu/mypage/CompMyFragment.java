package com.neomu.neomu.mypage;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.neomu.neomu.club.MainFragment;


public class CompMyFragment extends MyMainFragment {


    public CompMyFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

<<<<<<< HEAD
        Query compPostsQuery = databaseReference.child("posts").orderByChild("category").equalTo("게임");

        return compPostsQuery;
=======
        Query recentPostsQuery = databaseReference.child("posts")
                .limitToFirst(100);

        return recentPostsQuery;
>>>>>>> parent of cbe0f03... 업데이트
    }
}
