package com.hoavy.orapp.ui.Category;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.models.dtos.response.CategoriesResponse;
import com.hoavy.orapp.models.dtos.response.CategoryResponse;
import com.hoavy.orapp.models.dtos.response.PostsResponse;
import com.hoavy.orapp.repositories.CategoryRepository;
import com.hoavy.orapp.repositories.PostsRepository;
import com.hoavy.orapp.ui.viewmodels.CategoryCardViewModel;
import com.hoavy.orapp.ui.viewmodels.CategoryCircle;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModel extends ViewModel {
    private CategoryRepository categoryRepository;
    private LiveData<CategoriesResponse> categoriesResponseLiveData;
    private LiveData<PostsResponse> postsResponseLiveData;
    private LiveData<String> mCurrentCategoryTitle;

    public CategoryViewModel() { }

    public void init() {
        categoryRepository = new CategoryRepository();
        categoriesResponseLiveData = categoryRepository.getCategoriesResponseLiveData();
        postsResponseLiveData = categoryRepository.getPostsByCategoryResponse();
        mCurrentCategoryTitle = categoryRepository.getCurrentCategoryTitleLiveData();
    }

    public void getPostsByCategory(String id) { categoryRepository.getPostsByCategory(id); }

    public void getCategories() {
        categoryRepository.getCategories();
    }

    public LiveData<PostsResponse> getPostsResponseLiveData() {
        return postsResponseLiveData;
    }

    public LiveData<CategoriesResponse> getCategoriesResponseLiveData() {
        return categoriesResponseLiveData;
    }

    public LiveData<String> getCurrentCategoryTitle() {
        return mCurrentCategoryTitle;
    }
}