package com.hoavy.orapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hoavy.orapp.R;
import com.hoavy.orapp.activities.CreateCategoryActivity;
import com.hoavy.orapp.models.dtos.response.CategoryResponse;

import java.util.ArrayList;
import java.util.List;

public class DbCategoryAdapter extends RecyclerView.Adapter<DbCategoryAdapter.DbCategoryHolder> {

    private List<CategoryResponse> categoryResponses = new ArrayList<>();
    private Context context;
    ActivityResultLauncher<Intent> activityResultLauncher;

    public DbCategoryAdapter(Context context, ActivityResultLauncher<Intent> activityResultLauncher) {
        this.context = context;
        this.activityResultLauncher = activityResultLauncher;
    }

    public void setCategoryResponses(List<CategoryResponse> categoryResponses) {
        this.categoryResponses = categoryResponses;
    }

    @NonNull
    @Override
    public DbCategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category, parent, false);

        return new DbCategoryHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DbCategoryHolder holder, int position) {
        CategoryResponse cateResponse = categoryResponses.get(position);
        holder.categoryTitle.setText(cateResponse.getName());
        Glide.with(holder.itemView)
                .load(cateResponse.getFeaturedImage())
                .into(holder.categoryImage);
        holder.selectButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, CreateCategoryActivity.class);
            activityResultLauncher.launch(intent);
        });
    }

    @Override
    public int getItemCount() {
        return categoryResponses.size();
    }

    public class DbCategoryHolder extends RecyclerView.ViewHolder {
        private ImageView categoryImage;
        private TextView categoryTitle;
        private Button selectButton;

        public DbCategoryHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryTitle = itemView.findViewById(R.id.categoryName);
            selectButton = itemView.findViewById(R.id.selectCategory);
        }
    }
}
