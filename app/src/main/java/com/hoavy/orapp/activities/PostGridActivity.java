package com.hoavy.orapp.activities;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;

import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;
import com.hoavy.orapp.R;
import com.hoavy.orapp.adapters.PostGridAdapter;
import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.databinding.ActivityPostGridBinding;
import com.hoavy.orapp.models.dtos.response.PostsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostGridActivity extends AppCompatActivity {

    ActivityPostGridBinding binding;
    PostGridAdapter postAdapter;
    RetrofitAPI mRetrofitAPI;
    RecyclerView recyclerPosts;
    Skeleton skeletonPosts;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postAdapter = new PostGridAdapter(this, activityResultLauncher);
        mRetrofitAPI = ApiUtils.getAPIService();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Manage Posts");

        binding = ActivityPostGridBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        recyclerPosts = binding.recyclerPosts;
        recyclerPosts.setLayoutManager(new GridLayoutManager(this, 2));
        skeletonPosts = SkeletonLayoutUtils.applySkeleton(recyclerPosts, R.layout.item_post_grid);
        skeletonPosts.showSkeleton();

        loadPosts();
    }


    private void loadPosts() {
        mRetrofitAPI.getPosts().enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                if (response.body() != null)  {
                    skeletonPosts.showOriginal();
                    postAdapter.setPostResponses(response.body().getContent());
                    recyclerPosts.setAdapter(postAdapter);
                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {

            }
        });
    }
}