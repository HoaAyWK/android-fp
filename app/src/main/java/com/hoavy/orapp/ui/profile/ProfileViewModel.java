package com.hoavy.orapp.ui.profile;

import android.content.Context;
import android.text.TextUtils;

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
    private LiveData<PostsResponse> availablePostResponses;
    private LiveData<PostsResponse> processingPostResponses;
    private LiveData<PostsResponse> finishedPostResponses;
    private LiveData<PostsResponse> freelancerProcessingPostResponses;
    private PostsAuthRepository postsRepository;

    public ProfileViewModel() {
        mName = new MutableLiveData<>();
        mImage = new MutableLiveData<>();
    }

    public void init(Context context, String userRole) {
        postsRepository = new PostsAuthRepository(context, userRole);
        if (TextUtils.equals(userRole, "Freelancer")) {
            freelancerProcessingPostResponses = postsRepository.getFreelancerProcessingPostsLiveData();
        } else if(TextUtils.equals(userRole, "Customer")) {
            availablePostResponses = postsRepository.getCustomerLiveData();
            processingPostResponses = postsRepository.getProcessingPostsLiveData();
            finishedPostResponses = postsRepository.getFinishedPostsLiveData();
        }
    }

    public void getPosts() {
        postsRepository.getCustomerPosts();
    }

    public void getProcessingPosts() { postsRepository.getProcessingPosts(); }

    public void getFinishedPosts() { postsRepository.getFinishedPosts(); }

    public void getFreelancerProcessingPosts() { postsRepository.getFreelancerProcessingPosts(); }

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

    public LiveData<PostsResponse> getAvailablePostResponses() {
        return availablePostResponses;
    }

    public LiveData<PostsResponse> getProcessingPostResponses() {
        return processingPostResponses;
    }

    public LiveData<PostsResponse> getFinishedPostResponses() { return finishedPostResponses; }

    public LiveData<PostsResponse> getFreelancerProcessingPostResponse() { return freelancerProcessingPostResponses; }
}
