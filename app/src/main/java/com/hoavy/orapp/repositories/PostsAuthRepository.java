package com.hoavy.orapp.repositories;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.models.dtos.response.PostsResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsAuthRepository {
    private RetrofitAPI mRetrofitAPI;
    private MutableLiveData<PostsResponse> customerPostsLiveData;
    private MutableLiveData<PostsResponse> processingPostsLiveData;
    private MutableLiveData<PostsResponse> finishedPostsLiveData;

    public PostsAuthRepository(Context context) {
        mRetrofitAPI = ApiUtils.getAuthAPIService(context);
        customerPostsLiveData = new MutableLiveData<>();
        processingPostsLiveData = new MutableLiveData<>();
        finishedPostsLiveData = new MutableLiveData<>();

        getCustomerPosts();
        getProcessingPosts();
        getFinishedPosts();
    }

    public void getFinishedPosts() {
        mRetrofitAPI.getCustomerFinishedPosts().enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                if (response.body() != null) {
                    finishedPostsLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                finishedPostsLiveData.postValue(null);
            }
        });
    }

    public void getProcessingPosts() {
        mRetrofitAPI.getCustomerProcessingPosts().enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                if (response.body() != null) {
                    processingPostsLiveData.postValue((response.body()));
                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                processingPostsLiveData.postValue(null);
            }
        });
    }

    public void getCustomerPosts() {
        mRetrofitAPI.getCustomerPosts().enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                if (response.body() != null) {
                    customerPostsLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                customerPostsLiveData.postValue(null);
            }
        });
    }

    public LiveData<PostsResponse> getCustomerLiveData() {
        return customerPostsLiveData;
    }
    public LiveData<PostsResponse> getProcessingPostsLiveData() { return processingPostsLiveData; }
    public LiveData<PostsResponse> getFinishedPostsLiveData() { return  finishedPostsLiveData; }
}
