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

    public PostsAuthRepository(Context context) {
        mRetrofitAPI = ApiUtils.getAuthAPIService(context);
        customerPostsLiveData = new MutableLiveData<>();
        getCustomerPosts();
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
}
