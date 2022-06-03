package com.hoavy.orapp.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hoavy.orapp.R;
import com.hoavy.orapp.models.dtos.response.UserResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.CustomerHolder> {
    private List<UserResponse> userResponses;
    private static final int LIST_ITEM = 0;
    private static final int GRID_ITEM = 1;
    private Context context;

    boolean isSwitchView = true;

    public CustomerAdapter(Context context) {
        this.context = context;
    }

    public void setUserResponses(List<UserResponse> userResponses) {
        this.userResponses = userResponses;
    }

    public boolean toggleItemViewType() {
        isSwitchView = !isSwitchView;
        return isSwitchView;
    }

    @NonNull
    @Override
    public CustomerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;

        if (viewType == LIST_ITEM) {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_user, null);
        } else {
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_grid, null);
        }
        return new CustomerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomerHolder holder, int position) {
        UserResponse user = userResponses.get(position);

        if (user.getFeaturedAvatar() != null && !TextUtils.isEmpty(user.getFeaturedAvatar())) {
            Glide.with(holder.itemView)
                    .load(user.getFeaturedAvatar())
                    .into(holder.userAvatar);
        }

        String name = user.getFirstName() + " " + user.getLastName();
        holder.userName.setText(name);

        holder.showMore.setOnClickListener(v -> {

        });
    }

    @Override
    public int getItemCount() {
        return userResponses.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (isSwitchView) {
            return LIST_ITEM;
        } else {
            return GRID_ITEM;
        }
    }

    public class CustomerHolder extends RecyclerView.ViewHolder {


        TextView userName;


        ImageView userAvatar;


        ImageButton showMore;

        public CustomerHolder(@NonNull View itemView) {
            super(itemView);
            userAvatar = itemView.findViewById(R.id.userAvatarItem);
            userName = itemView.findViewById(R.id.userNameItem);
            showMore = itemView.findViewById(R.id.showMoreUser);
        }
    }

}
