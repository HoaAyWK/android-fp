package com.hoavy.orapp.ui.postdetail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hoavy.orapp.models.dtos.response.SinglePostResponse;
import com.hoavy.orapp.repositories.PostRepository;

public class PostDetailViewModel extends ViewModel {
    private LiveData<SinglePostResponse> postResponseLiveData;
    private PostRepository postRepository;

    public PostDetailViewModel() { }

    public void init(String id) {
        postRepository = new PostRepository();
        postResponseLiveData = postRepository.getPostResponseLiveData();
        getPost(id);
    }

    public void getPost(String id) {
        postRepository.getPost(id);
    }

    public LiveData<SinglePostResponse> getPostResponseLiveData() {
        return postResponseLiveData;
    }
}