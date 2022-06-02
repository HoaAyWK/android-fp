package com.hoavy.orapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;
import com.hoavy.orapp.R;
import com.hoavy.orapp.adapters.CategoryAdapter;
import com.hoavy.orapp.adapters.HighestPricePostAdapter;
import com.hoavy.orapp.adapters.PostAdapter;
import com.hoavy.orapp.databinding.FragmentHomeBinding;
import com.hoavy.orapp.models.dtos.response.CategoriesResponse;
import com.hoavy.orapp.models.dtos.response.PostsResponse;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    HomeViewModel homeViewModel;
    PostAdapter adapter;
    HighestPricePostAdapter hpAdapter;
    CategoryAdapter categoryAdapter;

    private Skeleton skeletonLatestPosts;
    private Skeleton skeletonHighestPosts;
    private Skeleton skeletonCategories;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 110) {
                        homeViewModel.getPosts();
                        homeViewModel.getHighestPricePosts();
                    }
                }
            });

    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PostAdapter(getContext(), activityResultLauncher);
        hpAdapter = new HighestPricePostAdapter(getContext());
        categoryAdapter = new CategoryAdapter();

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.init();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        RecyclerView recyclerView = binding.recyclerLatestPosts;
        RecyclerView recyclerHighestPricePost = binding.recyclerHighestPricePosts;
        RecyclerView recyclerCategories = binding.recyclerHomeCategories;

        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);

        skeletonLatestPosts = SkeletonLayoutUtils.applySkeleton(recyclerView, R.layout.item_latest_post);
        skeletonLatestPosts.showSkeleton();

        homeViewModel.getPostResponses().observe(getViewLifecycleOwner(), new Observer<PostsResponse>() {
            @Override
            public void onChanged(PostsResponse postResponses) {
                if (postResponses != null) {
                    adapter.setPostResponses(postResponses.getContent());
                    skeletonLatestPosts.showOriginal();
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        recyclerHighestPricePost.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerHighestPricePost.setHasFixedSize(true);

        skeletonHighestPosts = SkeletonLayoutUtils.applySkeleton(recyclerHighestPricePost, R.layout.item_highest_price_post);
        skeletonHighestPosts.showSkeleton();

        homeViewModel.getHighestPricePostsResponse().observe(getViewLifecycleOwner(), new Observer<PostsResponse>() {
            @Override
            public void onChanged(PostsResponse postsResponse) {
                if (postsResponse != null) {
                    hpAdapter.setPostResponses(postsResponse.getContent());
                    skeletonHighestPosts.showOriginal();
                    recyclerHighestPricePost.setAdapter(hpAdapter);
                }
            }
        });

        recyclerCategories.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerCategories.setHasFixedSize(true);

        skeletonCategories = SkeletonLayoutUtils.applySkeleton(recyclerCategories, R.layout.item_category_card);
        skeletonCategories.showSkeleton();

        homeViewModel.getCategoriesResponse().observe(getViewLifecycleOwner(), new Observer<CategoriesResponse>() {
            @Override
            public void onChanged(CategoriesResponse categoriesResponse) {
                if (categoriesResponse != null) {
                    categoryAdapter.setCategoryResponses(categoriesResponse.getContent());
                    skeletonCategories.showOriginal();
                    recyclerCategories.setAdapter(categoryAdapter);
                }
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}