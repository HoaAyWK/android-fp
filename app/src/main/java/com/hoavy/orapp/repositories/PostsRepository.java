package com.hoavy.orapp.repositories;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.models.dtos.response.PostResponse;
import com.hoavy.orapp.models.dtos.response.PostsResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostsRepository {
    private RetrofitAPI mRetrofitAPI;
    private MutableLiveData<PostsResponse> postResponsesLiveData;
    private MutableLiveData<PostsResponse> highestPricePostsLiveData;


    public PostsRepository() {
        mRetrofitAPI = ApiUtils.getAPIService();
        postResponsesLiveData = new MutableLiveData<>();
        highestPricePostsLiveData = new MutableLiveData<>();

        getHighestPricePosts();
        getPosts();
    }

    public void getHighestPricePosts() {
        mRetrofitAPI.getHighestPricePosts().enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                if (response.body() != null) {
                    highestPricePostsLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                highestPricePostsLiveData.postValue(null);
            }
        });
    }

    public void getPosts() {
        mRetrofitAPI.getPosts().enqueue(new Callback<PostsResponse>() {
            @Override
            public void onResponse(Call<PostsResponse> call, Response<PostsResponse> response) {
                if (response.body() != null) {
                    postResponsesLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<PostsResponse> call, Throwable t) {
                postResponsesLiveData.postValue(null);
            }
        });
    }


    public LiveData<PostsResponse> getHighestPicePosts() {
        return highestPricePostsLiveData;
    }

    public LiveData<PostsResponse> getPostResponses() {
        return postResponsesLiveData;
    }

}
