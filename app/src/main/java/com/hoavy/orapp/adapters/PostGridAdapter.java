package com.hoavy.orapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hoavy.orapp.PostDetialActivity;
import com.hoavy.orapp.R;
import com.hoavy.orapp.models.dtos.response.PostResponse;

import java.util.ArrayList;
import java.util.List;

public class PostGridAdapter extends RecyclerView.Adapter<PostGridAdapter.PostGridHolder> {

    private List<PostResponse> postResponses = new ArrayList<>();
    private Context context;
    private ActivityResultLauncher<Intent> activityResultLauncher;

    public PostGridAdapter(Context context, ActivityResultLauncher<Intent> activityResultLauncher) {
        this.context = context;
        this.activityResultLauncher = activityResultLauncher;
    }

    public void setPostResponses(List<PostResponse> postResponses) {
        this.postResponses = postResponses;
    }

    @NonNull
    @Override
    public PostGridHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post_grid, parent, false);
        return new PostGridHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostGridHolder holder, int position) {
        PostResponse post = postResponses.get(position);

        Glide.with(holder.itemView)
                .load(post.getFeaturedImage())
                .into(holder.postImage);

        String authorAvatar = post.getAuthor().getFeaturedAvatar();
        if (authorAvatar != null && !TextUtils.isEmpty(authorAvatar)) {
            Glide.with((holder.itemView))
                    .load(authorAvatar)
                    .into(holder.ownerAvatar);
        }

        holder.postTitle.setText(post.getTitle());
        holder.postCard.setOnClickListener(v -> {
            Intent intent = new Intent(context, PostDetialActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("id", post.getId());
            intent.putExtra("post", bundle);

            activityResultLauncher.launch(intent);
        });
    }

    @Override
    public int getItemCount() {
        return postResponses.size();
    }

    public class PostGridHolder extends RecyclerView.ViewHolder {

        private CardView postCard;
        private ImageView ownerAvatar;
        private ImageView postImage;
        private TextView postTitle;

        public PostGridHolder(@NonNull View itemView) {
            super(itemView);

            postCard = itemView.findViewById(R.id.postCardGrid);
            postImage = itemView.findViewById(R.id.postImageGrid);
            postTitle = itemView.findViewById(R.id.postTitleGrid);
            ownerAvatar = itemView.findViewById(R.id.ownerAvatarGrid);
        }
    }
}
