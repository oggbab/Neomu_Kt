package com.neomu.neomu.app.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.neomu.neomu.R;
import com.neomu.neomu.app.models.Post;

import androidx.recyclerview.widget.RecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_postTitle) TextView tv_postTitle;
    @BindView(R.id.tv_postCategory) TextView tv_postCategory;
    @BindView(R.id.iv_star) ImageView iv_star;
    @BindView(R.id.tv_postNumStars) TextView tv_postNumStars;
    @BindView(R.id.tv_postBody) TextView tv_postBody;
    @BindView(R.id.tv_postPrice) TextView tv_postPrice;
    @BindView(R.id.tv_postDate) TextView tv_postDate;
    @BindView(R.id.tv_postTime) TextView tv_postTime;
    @BindView(R.id.tv_postLocation) TextView tv_postLocation;

    public PostViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        tv_postTitle.setText(post.title);
        tv_postNumStars.setText(String.valueOf(post.likeCount));
        tv_postBody.setText(post.body);
        tv_postPrice.setText("#"+post.price+"천원");
        tv_postLocation.setText("#"+"홍대입구역");
        tv_postDate.setText(post.date);
        tv_postTime.setText(post.time);
        tv_postCategory.setText(post.category);

        iv_star.setOnClickListener(starClickListener);
    }
}
