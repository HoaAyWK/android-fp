package com.hoavy.orapp.repositories;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.models.dtos.response.UsersResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private RetrofitAPI mRetrofitAPI;
    private MutableLiveData<UsersResponse> usersResponseLiveData;

    public UserRepository(Context context) {
        usersResponseLiveData = new MutableLiveData<>();
        mRetrofitAPI = ApiUtils.getAuthAPIService(context);
        getUsers();
    }

    public void getUsers() {
        mRetrofitAPI.getRecentRegister(3).enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                if (response.body() != null) {
                    usersResponseLiveData.postValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {
                usersResponseLiveData.postValue(null);
                Log.d("GetUsersInUserRepo", t.toString());
            }
        });
    }

    public LiveData<UsersResponse> getUsersResponseLiveData() {
        return usersResponseLiveData;
    }
}
