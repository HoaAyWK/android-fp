package com.hoavy.orapp.ui.Category;

import com.hoavy.orapp.R;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;
import com.hoavy.orapp.adapters.CateAdapter;
import com.hoavy.orapp.adapters.PostCardAdapter;
import com.hoavy.orapp.databinding.CategoryFragmentBinding;
import com.hoavy.orapp.models.dtos.response.CategoriesResponse;
import com.hoavy.orapp.models.dtos.response.PostsResponse;
import com.hoavy.orapp.ui.home.HomeViewModel;


public class CategoryFragment extends Fragment {
    private CategoryFragmentBinding binding;
    private CateAdapter adapter;
    private PostCardAdapter postCardAdapter;
    CategoryViewModel categoryViewModel;
    HomeViewModel homeViewModel;

    private Skeleton skeletonCategories;
    private Skeleton skeletonPostCards;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 110) {
                        homeViewModel.getPosts();
                        homeViewModel.getHighestPricePosts();
                        String id = categoryViewModel.getCurrentCategoryId().getValue();
                        categoryViewModel.getPostsByCategory(id);
                    }
                }
            });

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postCardAdapter = new PostCardAdapter(getContext(), activityResultLauncher);

        categoryViewModel = new ViewModelProvider(requireActivity())
                .get(CategoryViewModel.class);
        homeViewModel = new ViewModelProvider(requireActivity())
                .get(HomeViewModel.class);
        categoryViewModel.init();
        adapter = new CateAdapter(categoryViewModel);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CategoryFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        RecyclerView recyclerCategoriesCircle = binding.recyclerCategoriesCircle;

        TextView currentCategoryTitle = binding.currentCategoryTitle;

        recyclerCategoriesCircle.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerCategoriesCircle.setHasFixedSize(true);
        skeletonCategories = SkeletonLayoutUtils.applySkeleton(recyclerCategoriesCircle, R.layout.item_category_circle);
        skeletonCategories.showSkeleton();

        categoryViewModel.getCategoriesResponseLiveData().observe(getViewLifecycleOwner(),
                new Observer<CategoriesResponse>() {
                    @Override
                    public void onChanged(CategoriesResponse categoriesResponse) {
                        if (categoriesResponse != null) {
                            skeletonCategories.showOriginal();
                            adapter.setCategoryResponses(categoriesResponse.getContent());
                            recyclerCategoriesCircle.setAdapter(adapter);
                        }
                    }
                });



        RecyclerView recyclerPostCards = binding.recyclerPostCards;
        recyclerPostCards.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerPostCards.setHasFixedSize(true);

        skeletonPostCards = SkeletonLayoutUtils.applySkeleton(recyclerPostCards, R.layout.item_post_card);
        skeletonPostCards.showSkeleton();

        categoryViewModel.getPostsResponseLiveData().observe(getViewLifecycleOwner(),
                new Observer<PostsResponse>() {
                    @Override
                    public void onChanged(PostsResponse postsResponse) {
                        if (postsResponse != null) {
                            skeletonPostCards.showOriginal();
                            postCardAdapter.setPostResponses(postsResponse.getContent());
                            recyclerPostCards.setAdapter(postCardAdapter);
                        }
                    }
                });

        categoryViewModel.getCurrentCategoryTitle().observe(getViewLifecycleOwner(),
                new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        currentCategoryTitle.setText(s);
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