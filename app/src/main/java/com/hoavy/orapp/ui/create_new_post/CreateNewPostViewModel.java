package com.hoavy.orapp.ui.create_new_post;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.hoavy.orapp.models.dtos.response.CategoriesResponse;
import com.hoavy.orapp.repositories.CategoryRepository;

public class CreateNewPostViewModel extends ViewModel {
    private CategoryRepository categoryRepository;
    private LiveData<CategoriesResponse> categoriesResponseLiveData;

    public CreateNewPostViewModel() { }

    public void init() {
        categoryRepository = new CategoryRepository();
        categoriesResponseLiveData = categoryRepository.getCategoriesResponseLiveData();
    }

    public LiveData<CategoriesResponse> getCategoriesResponseLiveData() {
        return categoriesResponseLiveData;
    }
}