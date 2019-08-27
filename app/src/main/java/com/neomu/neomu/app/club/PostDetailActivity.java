package com.neomu.neomu.app.club;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.neomu.neomu.R;
import com.neomu.neomu.app.chat.ChatActivity;
import com.neomu.neomu.app.club.util.CommentAdapter;
import com.neomu.neomu.app.models.Comment;
import com.neomu.neomu.app.models.Post;
import com.neomu.neomu.app.models.User;
import com.neomu.neomu.common.activity.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostDetailActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.tv_postCategory) TextView tv_postCategory;
    @BindView(R.id.tv_postTitle) TextView tv_postTitle;
    @BindView(R.id.tv_postBody) TextView tv_postBody;
    @BindView(R.id.tv_CommentText) TextView tv_CommentText;
    @BindView(R.id.rv_postDetail) RecyclerView rv_postDetail;
    @BindView(R.id.tv_postLocation) TextView tv_postLocation;
    @BindView(R.id.tv_postPrice) TextView tv_postPrice;
    @BindView(R.id.tv_postDate) TextView tv_postDate;
    @BindView(R.id.tv_postTime) TextView tv_postTime;

    private static final String TAG = "MyDetailActivity";
    public static final String EXTRA_POST_KEY = "post_key";

    private DatabaseReference mPostReference;
    private DatabaseReference mCommentsReference;
    private ValueEventListener mPostListener;
    private String mPostKey;
    private CommentAdapter mAdapter;
    private String authorName;
    private String title;
    private String author;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_detail);
        ButterKnife.bind(this);

        mPostKey = getIntent().getStringExtra(EXTRA_POST_KEY);
        if (mPostKey == null) {
            throw new IllegalArgumentException("Must pass EXTRA_POST_KEY");
        }

        mPostReference = FirebaseDatabase.getInstance().getReference()
                .child("posts").child(mPostKey);
        mCommentsReference = FirebaseDatabase.getInstance().getReference()
                .child("post-comments").child(mPostKey);

        rv_postDetail.setLayoutManager(new LinearLayoutManager(this));
        author = User.getNickName();
    }

    @Override
    public void onStart() {
        super.onStart();

        if (setPostListener()){
            mPostReference.addValueEventListener(mPostListener);
            mAdapter = new CommentAdapter(this, mCommentsReference);
            rv_postDetail.setAdapter(mAdapter);
        }
    }

    private boolean setPostListener() {

        mPostListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Post post = dataSnapshot.getValue(Post.class);
                tv_postCategory.setText(post.category);
                tv_postTitle.setText(post.title);
                //인식안되는 문제
                tv_postLocation.setText("#"+post.location);
                tv_postPrice.setText("#" + post.price + "천원");
                tv_postDate.setText(post.date);
                tv_postTime.setText(post.time);
                tv_postBody.setText(post.body);
                authorName = post.author;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                Toast.makeText(PostDetailActivity.this, "포스트를 로드하는데 실패",
                        Toast.LENGTH_SHORT).show();
            }
        };

        return true;
    }

    @Override
    public void onStop() {
        super.onStop();

        if (mPostListener != null) {
            mPostReference.removeEventListener(mPostListener);
        }

        mAdapter.cleanupListener();
    }

    private void postComment() {
        final String uid = getUid();
        FirebaseDatabase.getInstance().getReference().child("users").child(uid)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        String commentText = tv_CommentText.getText().toString();
                        if (commentText != null) {

                            Comment comment = new Comment(uid, author, commentText);
                            mCommentsReference.push().setValue(comment);
                        } else {
                            showToast("내용을 입력해주세요",false);
                        }
                        tv_CommentText.setText(null);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    void show() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("JoinClub");
        builder.setMessage("가입하시겠습니까?");
        builder.setPositiveButton("가입하기",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        postComment();
                        title = tv_postTitle.getText().toString();

                        if (author == null) author = "guest 님";
                        Intent intent = new Intent(PostDetailActivity.this, ChatActivity.class);
                        intent.putExtra("chatName", title);
                        intent.putExtra("AdminName", authorName);
                        intent.putExtra("userName", author);
                        startActivity(intent);
                        finish();
                    }
                });
        builder.setNegativeButton("취소",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        showToast("다음기회에",true);
                    }
                });
        builder.show();
    }

    @OnClick({R.id.btn_postJoin,R.id.btn_PostComment})
    public void onClick(View v) {
        int i = v.getId();
        switch (i) {
            case R.id.btn_PostComment:
                postComment();
                break;

            case R.id.btn_postJoin:
                // 가입된 아이디 체크 유효성 검사
                title = tv_postTitle.getText().toString();

                if (author == null) author = "guest 님";
                String result = "0";

                if (result.equals("0")) {
                    show();

                } else if(result=="1") {
                    Intent intent = new Intent(PostDetailActivity.this, ChatActivity.class);
                    intent.putExtra("chatName", title);
                    intent.putExtra("AdminName", authorName);
                    intent.putExtra("userName", author);
                    startActivity(intent);
                    finish();
                }else{
                    showToast("코드에러",false);
                }

                break;
        }
    }
}