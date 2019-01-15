package com.neomu.neomu.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.neomu.neomu.R;
import com.neomu.neomu.models.Post;

import androidx.recyclerview.widget.RecyclerView;

public class PostViewHolder extends RecyclerView.ViewHolder {

    public TextView titleView;
    public TextView categoryView;
    public ImageView starView;
    public TextView numStarsView;
    public TextView bodyView;
    TextView postPrice,postPeople,postLocation,postDate,postTime;

    public PostViewHolder(View itemView) {
        super(itemView);

        titleView = itemView.findViewById(R.id.postTitle);
        categoryView = itemView.findViewById(R.id.postCategory);
        starView = itemView.findViewById(R.id.star);
        numStarsView = itemView.findViewById(R.id.postNumStars);
        bodyView = itemView.findViewById(R.id.postBody);
        postPrice = itemView.findViewById(R.id.postPrice);
//        postPeople = itemView.findViewById(R.id.postPeople);
        postLocation = itemView.findViewById(R.id.postLocation);

        postDate = itemView.findViewById(R.id.postDate);
        postTime = itemView.findViewById(R.id.postTime);
    }

    public void bindToPost(Post post, View.OnClickListener starClickListener) {
        titleView.setText(post.title);
        numStarsView.setText(String.valueOf(post.likeCount));
        bodyView.setText(post.body);

        postPrice.setText("#"+post.price+"천원");
//        postPeople.setText(post.people);
        postLocation.setText("#"+"홍대입구역");
        postDate.setText(post.date);
        postTime.setText(post.time);
        categoryView.setText(post.category);

        starView.setOnClickListener(starClickListener);
    }
}
