package com.hoavy.orapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hoavy.orapp.models.dtos.response.CategoriesResponse;
import com.hoavy.orapp.models.dtos.response.CategoryResponse;
import com.hoavy.orapp.models.dtos.response.PostResponse;
import com.hoavy.orapp.models.dtos.response.PostsResponse;
import com.hoavy.orapp.repositories.CategoryRepository;
import com.hoavy.orapp.repositories.PostsRepository;

import java.util.List;

public class HomeViewModel extends ViewModel {
    private PostsRepository postsRepository;
    private CategoryRepository categoryRepository;
    private LiveData<PostsResponse> postResponses;
    private LiveData<PostsResponse> highestPricePosts;
    private LiveData<CategoriesResponse> categoriesResponse;
    public HomeViewModel() { }

    public void init() {
        postsRepository = new PostsRepository();
        categoryRepository = new CategoryRepository();
        postResponses = postsRepository.getPostResponses();
        highestPricePosts = postsRepository.getHighestPicePosts();
        categoriesResponse = categoryRepository.getCategoriesResponseLiveData();
    }

    public void getPosts() {
        postsRepository.getPosts();
    }
    public void getHighestPricePosts() { postsRepository.getHighestPricePosts(); }
    public void getCategories() { categoryRepository.getCategories(); }

    public LiveData<PostsResponse> getPostResponses() {
        return postResponses;
    }
    public LiveData<PostsResponse> getHighestPricePostsResponse() { return highestPricePosts; }
    public LiveData<CategoriesResponse> getCategoriesResponse() { return categoriesResponse; }
}