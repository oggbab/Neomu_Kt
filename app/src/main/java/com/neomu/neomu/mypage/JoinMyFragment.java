package com.neomu.neomu.mypage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
<<<<<<< HEAD
=======
import com.neomu.neomu.R;
import com.neomu.neomu.club.MainFragment;
>>>>>>> parent of cbe0f03... 업데이트

public class JoinMyFragment extends MyMainFragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_join, container, false);
    }

    public JoinMyFragment() {
    }

    @Override
    public Query getQuery(DatabaseReference databaseReference) {

        Query recentPostsQuery = databaseReference.child("")
                .limitToFirst(1);

        return recentPostsQuery;

    }
}