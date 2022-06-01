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

import java.util.ArrayList;
import java.util.List;

public class HighestPricePostAdapter
        extends RecyclerView.Adapter<HighestPricePostAdapter.HighestPricePostHolder> {

    private List<PostResponse> postResponses = new ArrayList<>();

    public HighestPricePostAdapter() { }

    public void setPostResponses(List<PostResponse> postResponses) {
        this.postResponses = postResponses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HighestPricePostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_highest_price_post, parent, false);
        return new HighestPricePostHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HighestPricePostHolder holder, int position) {
        PostResponse postResponse = postResponses.get(position);
        String ownerName = postResponse.getAuthor().getFirstName()
                + " " + postResponse.getAuthor().getLastName();
        holder.tvTitle.setText(postResponse.getTitle());
        holder.tvOwnerName.setText(ownerName);
        if (postResponse.getFeaturedImage() != null) {
            Glide.with(holder.itemView)
                    .load(postResponse.getFeaturedImage())
                    .into(holder.imgPost);
        }

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

    public class HighestPricePostHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private TextView tvOwnerName;
        private ImageView imgPost;
        private ImageView imgOwnerAvatar;

        public HighestPricePostHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.highestPriceTitle);
            tvOwnerName = itemView.findViewById(R.id.tvOwnerHPName);
            imgPost = itemView.findViewById(R.id.imgHighestPricePost);
            imgOwnerAvatar = itemView.findViewById(R.id.imgOwnerHPPostAvatar);
        }
    }
}
