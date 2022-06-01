package com.hoavy.orapp.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.models.dtos.response.CategoriesResponse;
import com.hoavy.orapp.models.dtos.response.PostsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private RetrofitAPI mRetrofitAPI;
    private MutableLiveData<CategoriesResponse> categoriesResponseLiveData;
    private MutableLiveData<PostsResponse> postsByCategoryResponseLiveData;
    private MutableLiveData<String> currentCategoryTitleLiveData;

    public CategoryRepository() {
        categoriesResponseLiveData = new MutableLiveData<>();
        postsByCategoryResponseLiveData = new MutableLiveData<>();
        currentCategoryTitleLiveData = new MutableLiveData<>();
        mRetrofitAPI = ApiUtils.getAPIService();
        getCategories();
    }

    public void setCurrentCategoryTitleLiveData(String title) {
        currentCategoryTitleLiveData.postValue(title);
    }

    public void getCategories() {
        mRetrofitAPI.getCategories().enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if (response.body() != null) {
                    categoriesResponseLiveData.postValue(response.body());
                    getPostsByCategory(response.body().getContent().get(0).getId());
                    setCurrentCategoryTitleLiveData(response.body().getContent().get(0).getName());
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {
                categoriesResponseLiveData.postValue(null);
                Log.d("GetCategoriesFromRepo", t.toString());
            }
        });
    }

    public void getPostsByCategory(String id) {
        mRetrofitAPI.getPostsByCategory(id).enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                if (response.body() != null) {
                    postsByCategoryResponseLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                postsByCategoryResponseLiveData.postValue(null);
            }
        });
    }

    public LiveData<CategoriesResponse> getCategoriesResponseLiveData() {
        return categoriesResponseLiveData;
    }

    public LiveData<PostsResponse> getPostsByCategoryResponse() { return postsByCategoryResponseLiveData; }

    public LiveData<String> getCurrentCategoryTitleLiveData() { return currentCategoryTitleLiveData; }
}
