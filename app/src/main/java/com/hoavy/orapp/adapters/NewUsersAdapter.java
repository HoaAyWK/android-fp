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
import com.hoavy.orapp.models.dtos.response.UserResponse;

import java.util.ArrayList;
import java.util.List;

public class NewUsersAdapter extends RecyclerView.Adapter<NewUsersAdapter.NewUserHolder> {
    private List<UserResponse> userResponses = new ArrayList<>();

    public NewUsersAdapter() { }

    public void setUserResponses(List<UserResponse> userResponses) {
        this.userResponses = userResponses;
    }

    @NonNull
    @Override
    public NewUserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View itemView = LayoutInflater.from(parent.getContext())
               .inflate(R.layout.item_new_user, parent, false);
       return new NewUserHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull NewUserHolder holder, int position) {
        UserResponse userResponse = userResponses.get(position);
        String fullName = userResponse.getFirstName() + " " + userResponse.getLastName();
        holder.tvUserName.setText(fullName);
        holder.tvUserTime.setText(userResponse.getCreatedDate());
        if (userResponse.getFeaturedAvatar() != null) {
            Glide.with(holder.itemView)
                    .load(userResponse.getFeaturedAvatar())
                    .into(holder.imgUserAvatar);
        }

    }

    @Override
    public int getItemCount() {
        return userResponses == null ? 0 : userResponses.size();
    }

    public class NewUserHolder extends RecyclerView.ViewHolder {
        private TextView tvUserTime;
        private TextView tvUserName;
        private ImageView imgUserAvatar;
        public NewUserHolder(@NonNull View itemView) {
            super(itemView);
            tvUserName = itemView.findViewById(R.id.tvNewUserName);
            tvUserTime = itemView.findViewById(R.id.tvNewUserTime);
            imgUserAvatar = itemView.findViewById(R.id.imgNewUser);
        }
    }
}
