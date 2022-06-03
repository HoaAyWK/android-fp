package com.hoavy.orapp.adapters;

import android.text.TextUtils;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'");
        Date date = new Date();
        try {
            date = sdf.parse(userResponse.getCreatedDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String formattedDate = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);
        holder.tvUserTime.setText(formattedDate);
        if (userResponse.getFeaturedAvatar() != null && !TextUtils.isEmpty(userResponse.getFeaturedAvatar())) {
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
