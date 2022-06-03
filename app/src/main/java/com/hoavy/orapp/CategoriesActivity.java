package com.hoavy.orapp;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;
import com.hoavy.orapp.activities.CreateCategoryActivity;
import com.hoavy.orapp.adapters.DbCategoryAdapter;
import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.databinding.ActivityCategoriesBinding;
import com.hoavy.orapp.models.dtos.response.CategoriesResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoriesActivity extends AppCompatActivity {

    ActivityCategoriesBinding binding;
    RetrofitAPI mRetrofitAPI;
    DbCategoryAdapter categoryAdapter;
    private Skeleton skeletonCategories;
    RecyclerView recyclerCategories;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 150 || result.getResultCode() == 170) {
                        loadCategories();
                    }

                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRetrofitAPI = ApiUtils.getAPIService();
        categoryAdapter = new DbCategoryAdapter(this, activityResultLauncher);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("Manage Categories");

        binding = ActivityCategoriesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerCategories = binding.recyclerCategories;
        recyclerCategories.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
        recyclerCategories.setHasFixedSize(true);

        skeletonCategories = SkeletonLayoutUtils.applySkeleton(recyclerCategories, R.layout.item_category);
        skeletonCategories.showSkeleton();

        loadCategories();

        binding.btnAddCategory.setOnClickListener(v -> {
            Intent intent = new Intent(this, CreateCategoryActivity.class);
            activityResultLauncher.launch(intent);
        });
    }


    public void loadCategories() {
        mRetrofitAPI.getCategories().enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if (response.body() != null) {
                    skeletonCategories.showOriginal();
                    categoryAdapter.setCategoryResponses(response.body().getContent());
                    recyclerCategories.setAdapter(categoryAdapter);
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return false;
    }
}