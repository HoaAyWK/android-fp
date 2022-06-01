package com.hoavy.orapp.ui.Category;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hoavy.orapp.adapters.CateAdapter;
import com.hoavy.orapp.adapters.PostCardAdapter;
import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.databinding.CategoryFragmentBinding;
import com.hoavy.orapp.models.dtos.response.CategoriesResponse;
import com.hoavy.orapp.models.dtos.response.CategoryResponse;
import com.hoavy.orapp.models.dtos.response.PostsResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {
    private CategoryFragmentBinding binding;
    private CateAdapter adapter;
    private PostCardAdapter postCardAdapter;
    CategoryViewModel categoryViewModel;

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new CateAdapter();
        postCardAdapter = new PostCardAdapter(getContext());

        categoryViewModel = new ViewModelProvider(requireActivity())
                .get(CategoryViewModel.class);
        categoryViewModel.init();
        categoryViewModel.getCategoriesResponseLiveData().observe(this,
                new Observer<CategoriesResponse>() {
                    @Override
                    public void onChanged(CategoriesResponse categoriesResponse) {
                        if (categoriesResponse != null) {
                            adapter.setCategoryResponses(categoriesResponse.getContent());
                        }
                    }
                });
        categoryViewModel.getPostsResponseLiveData().observe(this,
                new Observer<PostsResponse>() {
                    @Override
                    public void onChanged(PostsResponse postsResponse) {
                        if (postsResponse != null) {
                            postCardAdapter.setPostResponses(postsResponse.getContent());
                        }
                    }
                });
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CategoryFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerCategoriesCircle = binding.recyclerCategoriesCircle;
        RecyclerView recyclerPostCards = binding.recyclerPostCards;
        TextView currentCategoryTitle = binding.currentCategoryTitle;

        recyclerCategoriesCircle.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerCategoriesCircle.setHasFixedSize(true);
        recyclerCategoriesCircle.setAdapter(adapter);

        recyclerPostCards.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerPostCards.setHasFixedSize(true);
        recyclerPostCards.setAdapter(postCardAdapter);

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