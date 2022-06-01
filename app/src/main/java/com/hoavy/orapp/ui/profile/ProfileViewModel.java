package com.hoavy.orapp.ui.profile;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hoavy.orapp.models.dtos.response.PostsResponse;
import com.hoavy.orapp.repositories.PostsAuthRepository;
import com.hoavy.orapp.repositories.PostsRepository;
import com.hoavy.orapp.utils.SharedHelper;

public class ProfileViewModel extends ViewModel {
    private MutableLiveData<String> mName;
    private MutableLiveData<String> mImage;
    private LiveData<PostsResponse> postResponses;
    private PostsAuthRepository postsRepository;

    public ProfileViewModel() {
        mName = new MutableLiveData<>();
        mImage = new MutableLiveData<>();
    }

    public void init(Context context) {
        postsRepository = new PostsAuthRepository(context);
        postResponses = postsRepository.getCustomerLiveData();
    }

    public void getPosts() {
        postsRepository.getCustomerPosts();
    }

    public void setName(String name) {
        mName.setValue(name);
    }

    public void setmImage(String image) {
        mImage.setValue(image);
    }

    public LiveData<String> getNameLiveData() {
        return mName;
    }

    public LiveData<String> getImageLiveData() {
        return mImage;
    }

    public LiveData<PostsResponse> getPostResponses() {
        return postResponses;
    }
}
