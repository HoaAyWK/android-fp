package com.hoavy.orapp.adapters;

import static com.hoavy.orapp.utils.ImageUtils.LoadImageFromWebOperations;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hoavy.orapp.R;
import com.hoavy.orapp.models.dtos.response.CategoryResponse;
import com.hoavy.orapp.repositories.CategoryRepository;
import com.hoavy.orapp.ui.Category.CategoryViewModel;

import java.util.ArrayList;
import java.util.List;

public class CateAdapter extends RecyclerView.Adapter<CateAdapter.CateHolder> {
    private List<CategoryResponse> categoryResponses = new ArrayList<>();
    private CategoryRepository categoryRepository;
    private CategoryViewModel categoryViewModel;

    public CateAdapter(CategoryViewModel categoryViewModel) {
        this.categoryViewModel = categoryViewModel;
    }

    @NonNull
    @Override
    public CateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_category_circle, parent, false);
        return new CateHolder(itemView);
    }

    public void setCategoryResponses(List<CategoryResponse> categoryResponses) {
        this.categoryResponses = categoryResponses;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull CateHolder holder, int position) {
        CategoryResponse cateResponse = categoryResponses.get(position);
        holder.tvName.setText(cateResponse.getName());

        Glide.with(holder.itemView)
                .load(cateResponse.getFeaturedImage())
                .into(holder.imgButton);


        holder.imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryViewModel.getPostsByCategory(cateResponse.getId());
                categoryViewModel.setCategoryTitle(cateResponse.getName());
                categoryViewModel.setCategoryId(cateResponse.getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryResponses == null ? 0 : categoryResponses.size();
    }


    public class CateHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageButton imgButton;
        private CardView cateCard;
        public CateHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvCategoryNameCircle);
            imgButton = itemView.findViewById(R.id.btnCategoryCircle);
            cateCard = itemView.findViewById(R.id.cateCard);
        }
    }
}
