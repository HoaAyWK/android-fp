package com.hoavy.orapp.ui.dashboard;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hoavy.orapp.models.dtos.response.UsersResponse;
import com.hoavy.orapp.repositories.UserRepository;
import com.hoavy.orapp.ui.viewmodels.CategoryCardViewModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardViewModel extends ViewModel {
    private UserRepository userRepository;
    private LiveData<UsersResponse> usersResponseLiveData;
    public DashboardViewModel() { }

    public void init(Context context) {
        userRepository = new UserRepository(context);
        usersResponseLiveData = userRepository.getUsersResponseLiveData();
    }

    public void getUsers() {
        userRepository.getUsers();
    }

    public LiveData<UsersResponse> getUsersResponseLiveData() {
        return usersResponseLiveData;
    }
}