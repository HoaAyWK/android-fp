package com.hoavy.orapp.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hoavy.orapp.adapters.CategoryAdapter;
import com.hoavy.orapp.adapters.HighestPricePostAdapter;
import com.hoavy.orapp.adapters.PostAdapter;
import com.hoavy.orapp.databinding.FragmentHomeBinding;
import com.hoavy.orapp.models.dtos.response.CategoriesResponse;
import com.hoavy.orapp.models.dtos.response.PostResponse;
import com.hoavy.orapp.models.dtos.response.PostsResponse;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    HomeViewModel homeViewModel;
    PostAdapter adapter;
    HighestPricePostAdapter hpAdapter;
    CategoryAdapter categoryAdapter;


    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PostAdapter(getContext());
        hpAdapter = new HighestPricePostAdapter();
        categoryAdapter = new CategoryAdapter();

        homeViewModel = new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
        homeViewModel.init();
        homeViewModel.getPostResponses().observe(this, new Observer<PostsResponse>() {
            @Override
            public void onChanged(PostsResponse postResponses) {
                if (postResponses != null) {
                    adapter.setPostResponses(postResponses.getContent());
                }
            }
        });

        homeViewModel.getHighestPricePostsResponse().observe(this, new Observer<PostsResponse>() {
            @Override
            public void onChanged(PostsResponse postsResponse) {
                if (postsResponse != null) {
                    hpAdapter.setPostResponses(postsResponse.getContent());
                }
            }
        });

        homeViewModel.getCategoriesResponse().observe(this, new Observer<CategoriesResponse>() {
            @Override
            public void onChanged(CategoriesResponse categoriesResponse) {
                if (categoriesResponse != null) {
                    categoryAdapter.setCategoryResponses(categoriesResponse.getContent());
                }
            }
        });
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
        recyclerView.setAdapter(adapter);


        recyclerHighestPricePost.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerHighestPricePost.setHasFixedSize(true);
        recyclerHighestPricePost.setAdapter(hpAdapter);

        recyclerCategories.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerCategories.setHasFixedSize(true);
        recyclerCategories.setAdapter(categoryAdapter);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}