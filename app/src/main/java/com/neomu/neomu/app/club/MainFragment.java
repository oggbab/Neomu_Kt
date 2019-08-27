package com.neomu.neomu.app.club;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Query;
import com.google.firebase.database.Transaction;
import com.neomu.neomu.R;
import com.neomu.neomu.app.models.Post;
import com.neomu.neomu.app.viewholder.PostViewHolder;
import com.neomu.neomu.common.activity.fragment.BaseFragment;

import butterknife.ButterKnife;

public class MainFragment extends BaseFragment {

    private DatabaseReference mDatabaseReference;
    private FirebaseRecyclerAdapter<Post, PostViewHolder> mFireBaseRecyclerAdapter;
    private RecyclerView rv_main;
    private LinearLayoutManager mLinearLayoutManager;
    FloatingActionButton btn_fa;

    public MainFragment() {}

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container,
                              Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,rootView);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // db가져오기
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        //사이클뷰 세팅
        rv_main = view.findViewById(R.id.rv_main);
        rv_main.setHasFixedSize(true);

        //플로팅액션버튼 세팅
        btn_fa = view.findViewById(R.id.btn_fa);
        btn_fa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),Club_New_Activity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // 레이아웃 매니저
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mLinearLayoutManager.setReverseLayout(true);
        mLinearLayoutManager.setStackFromEnd(true);
        rv_main.setLayoutManager(mLinearLayoutManager);

        // 해당 쿼리 가져오기
        Query postsQuery = getQuery(mDatabaseReference);

        //쿼리로 세팅
        FirebaseRecyclerOptions options = new FirebaseRecyclerOptions.Builder<Post>()
                .setQuery(postsQuery, Post.class)
                .build();

        //어뎁터 뷰홀더 설정
        mFireBaseRecyclerAdapter = new FirebaseRecyclerAdapter<Post, PostViewHolder>(options) {

            @Override
            public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                return new PostViewHolder(inflater.inflate(R.layout.item_post, viewGroup, false));
            }

            @Override
            protected void onBindViewHolder(PostViewHolder viewHolder, int position, final Post model) {
                final DatabaseReference postRef = getRef(position);

                // Set click listener for the whole post view
                final String postKey = postRef.getKey();
                viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), PostDetailActivity.class);
                        intent.putExtra(PostDetailActivity.EXTRA_POST_KEY, postKey);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });

                // 좋아요 클릭하면 이미지 변경
                //todo 버터나이프로 인한 에러인듯
/*                if (model.like.containsKey(getUid())) {
                    viewHolder.iv_star.setImageResource(R.drawable.ic_like_on);
                } else {
                    viewHolder.starView.setImageResource(R.drawable.ic_like);
                }*/

                viewHolder.bindToPost(model, new View.OnClickListener() {
                    @Override
                    public void onClick(View starView) {
                        // Need to write to both places the post is stored
                        DatabaseReference globalPostRef = mDatabaseReference.child("posts").child(postRef.getKey());
                        DatabaseReference userPostRef = mDatabaseReference.child("user-posts").child(model.uid).child(postRef.getKey());

                        // Run two transactions
                        onStarClicked(globalPostRef);
                        onStarClicked(userPostRef);
                    }
                });
            }
        };
        rv_main.setAdapter(mFireBaseRecyclerAdapter);
    }

    // 좋아요 클릭했을때
    private void onStarClicked(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Post post = mutableData.getValue(Post.class);
                if (post == null) {
                    return Transaction.success(mutableData);
                }

                if (post.like.containsKey(getUid())) {
                    // 좋아요 취소
                    post.likeCount = post.likeCount - 1;
                    post.like.remove(getUid());
                } else {
                    // Star the post and add self to stars
                    post.likeCount = post.likeCount + 1;
                    post.like.put(getUid(), true);
                }

                // Set value and report transaction success
                mutableData.setValue(post);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {}
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mFireBaseRecyclerAdapter != null) {
            mFireBaseRecyclerAdapter.startListening();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mFireBaseRecyclerAdapter != null) {
            mFireBaseRecyclerAdapter.stopListening();
        }
    }

}
