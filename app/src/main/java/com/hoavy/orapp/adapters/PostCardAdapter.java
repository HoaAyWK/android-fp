package com.hoavy.orapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hoavy.orapp.R;
import com.hoavy.orapp.models.dtos.response.PostResponse;
import com.hoavy.orapp.repositories.CategoryRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PostCardAdapter extends RecyclerView.Adapter<PostCardAdapter.PostCardHolder> {

    private List<PostResponse> postResponses = new ArrayList<>();


    public PostCardAdapter() {

    }

    public void setPostResponses(List<PostResponse> postResponses) {
        this.postResponses = postResponses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post_card, parent, false);
        return new PostCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostCardHolder holder, int position) {
        PostResponse postResponse = postResponses.get(position);
        String ownerName = postResponse.getAuthor().getFirstName() + " " + postResponse.getAuthor().getLastName();
        String createdTime = postResponse.getCreatedDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
        Date date = new Date();
        try {
            date = sdf.parse(createdTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = new SimpleDateFormat("MM/dd/yyyy").format(date);

        holder.tvPostTitle.setText(postResponse.getTitle());
        holder.tvOwnerName.setText(ownerName);
        holder.tvCreatedTime.setText(formattedDate);

        Glide.with(holder.itemView)
                .load(postResponse.getFeaturedImage())
                .into(holder.imgPostCard);

        if (postResponse.getAuthor().getFeaturedAvatar() != null) {
            Glide.with(holder.itemView)
                    .load(postResponse.getAuthor().getFeaturedAvatar())
                    .into(holder.imgOwnerAvatar);
        }
    }

    @Override
    public int getItemCount() {
        return postResponses.size();
    }

    public class PostCardHolder extends RecyclerView.ViewHolder {
        private TextView tvPostTitle;
        private TextView tvOwnerName;
        private TextView tvCreatedTime;
        private ImageView imgPostCard;
        private ImageView imgOwnerAvatar;
        public PostCardHolder(@NonNull View itemView) {
            super(itemView);
            tvPostTitle = itemView.findViewById(R.id.tvPostCardTitle);
            tvCreatedTime = itemView.findViewById(R.id.tvPostCreatedTime);
            tvOwnerName = itemView.findViewById(R.id.tvOwnerPostName);
            imgPostCard = itemView.findViewById(R.id.imgPostCard);
            imgOwnerAvatar = itemView.findViewById(R.id.imgOwnerPostCard);
        }
    }
}
