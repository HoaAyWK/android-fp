package com.hoavy.orapp.adapters;

import static com.hoavy.orapp.utils.ImageUtils.LoadImageFromWebOperations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.hoavy.orapp.models.dtos.response.CategoryResponse;
import com.hoavy.orapp.ui.viewmodels.CategoryCardViewModel;

import java.util.ArrayList;
import java.util.List;


public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryCardHolder> {
    private List<CategoryResponse> categoryResponses = new ArrayList<>();

    public CategoryAdapter() { }

    public void setCategoryResponses(List<CategoryResponse> categoryResponses) {
        this.categoryResponses = categoryResponses;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryCardHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_card, parent, false);
        return new CategoryCardHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryCardHolder holder, int position) {
        CategoryResponse categoryResponse = categoryResponses.get(position);
        holder.tvTitle.setText(categoryResponse.getName());
        if (categoryResponse.getFeaturedImage() != null) {
            Glide.with(holder.itemView)
                    .load(categoryResponse.getFeaturedImage())
                    .into(holder.image);
        }
    }

    @Override
    public int getItemCount() {
       return  categoryResponses.size();
    }

    public class CategoryCardHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle;
        private ImageView image;
        public CategoryCardHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.category_card_title);
            image = itemView.findViewById(R.id.img_category_card);
        }
    }


}
