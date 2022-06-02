package com.hoavy.orapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.hoavy.orapp.PostActivity;
import com.hoavy.orapp.PostDetialActivity;
import com.hoavy.orapp.R;
import com.hoavy.orapp.models.dtos.response.PostResponse;
import com.hoavy.orapp.ui.home.HomeFragment;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostHolder> {
    private List<PostResponse> postResponses = new ArrayList<>();
    private Context context;
    ActivityResultLauncher<Intent> activityResultLauncher;
    public PostAdapter(Context context, ActivityResultLauncher<Intent> activityResultLauncher) {
        this.context = context;
        this.activityResultLauncher = activityResultLauncher;
    }

    public void setPostResponses(List<PostResponse> postResponses) {
        this.postResponses = postResponses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_latest_post, parent, false);
        return new PostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostHolder holder, int position) {
        PostResponse postResponse = postResponses.get(position);
        String userName = postResponse.getAuthor().getFirstName()
                + " " + postResponse.getAuthor().getLastName();
        String postTitle = postResponse.getTitle();
        holder.tvTitle.setText(postTitle);
        holder.tvUserName.setText(userName);

        if (postResponse.getAuthor().getFeaturedAvatar() != null) {
            String userAvatar = postResponse.getAuthor().getFeaturedAvatar().toString();
            Glide.with(holder.itemView)
                    .load(userAvatar)
                    .into(holder.imgAvatar);
        }
        if (postResponse.getFeaturedImage() != null) {
            Glide.with(holder.itemView)
                    .load(postResponse.getFeaturedImage())
                    .into(holder.imgPost);
        }
        holder.cardViewLastestPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostDetialActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id", postResponse.getId());

                if (postResponse.getAuthor().getPhone() == null) {
                    bundle.putString("authorPhone", "");
                } else {
                    bundle.putString("authorPhone", postResponse.getAuthor().getPhone());
                }

                if (postResponse.getAuthor().getFeaturedAvatar() == null) {
                    bundle.putString("authorAvatar", "");
                } else {
                    bundle.putString("authorAvatar", postResponse.getAuthor().getFeaturedAvatar().toString());
                }

                intent.putExtra("post", bundle);
                activityResultLauncher.launch(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return postResponses.size();
    }

    public class PostHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView imgPost;
        private ImageView imgAvatar;
        private TextView tvUserName;
        private CardView cardViewLastestPost;
        public PostHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.latestPostTitle);
            tvUserName = itemView.findViewById(R.id.tvPostUserName);
            imgPost = itemView.findViewById(R.id.imgLatestPost);
            imgAvatar = itemView.findViewById(R.id.imgOwnerLatestPostAvatar);
            cardViewLastestPost = itemView.findViewById(R.id.card_view_latest_post);
        }
    }
}
