package com.hoavy.orapp.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.models.dtos.response.SinglePostResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepository {
    private RetrofitAPI mRetrofitAPI;
    private MutableLiveData<SinglePostResponse> postResponseLiveData;

    public PostRepository() {
        mRetrofitAPI = ApiUtils.getAPIService();
        postResponseLiveData = new MutableLiveData<>();
    }

    public void getPost(String id) {
        mRetrofitAPI.getPost(id).enqueue(new Callback<SinglePostResponse>() {
            @Override
            public void onResponse(Call<SinglePostResponse> call, Response<SinglePostResponse> response) {
                if (response.body() != null) {
                    postResponseLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<SinglePostResponse> call, Throwable t) {
                Log.d("GetPostById", t.toString());
                postResponseLiveData.postValue(null);
            }
        });
    }

    public LiveData<SinglePostResponse> getPostResponseLiveData() {
        return postResponseLiveData;
    }
}
