package com.neomu.neomu.app.club.util;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.neomu.neomu.R;

class CommentViewHolder extends RecyclerView.ViewHolder {

    public TextView authorView;
    public TextView bodyView;

    public CommentViewHolder(View itemView) {
        super(itemView);

        authorView = itemView.findViewById(R.id.tv_commentAuthor);
        bodyView = itemView.findViewById(R.id.tv_commentBody);
    }
}