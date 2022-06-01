package com.hoavy.orapp.ui.create_new_post;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.hoavy.orapp.CreatePostActivity;
import com.hoavy.orapp.R;
import com.hoavy.orapp.databinding.CreateNewPostFragmentBinding;
import com.hoavy.orapp.models.dtos.response.CategoriesResponse;
import com.hoavy.orapp.models.dtos.response.CategoryResponse;

public class CreateNewPostFragment extends Fragment {
    private CreateNewPostFragmentBinding binding;
    private ArrayAdapter<CategoryResponse> categoryAdapter;
    private CreateNewPostViewModel createNewPostViewModel;
    private String categoryId = "";

    public static CreateNewPostFragment newInstance() {
        return new CreateNewPostFragment();
    }

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNewPostViewModel = new ViewModelProvider(this)
                .get(CreateNewPostViewModel.class);
        createNewPostViewModel.init();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = CreateNewPostFragmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        createNewPostViewModel.getCategoriesResponseLiveData().observe(getViewLifecycleOwner(),
                new Observer<CategoriesResponse>() {
                    @Override
                    public void onChanged(CategoriesResponse categoriesResponse) {
                        if (categoriesResponse != null) {
                            categoryAdapter = new ArrayAdapter<>(getContext(),
                                    android.R.layout.simple_spinner_item, categoriesResponse.getContent());
                            categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            binding.listCategoryAdd.setAdapter(categoryAdapter);
                            binding.listCategoryAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    CategoryResponse categoryResponse = (CategoryResponse) parent.getAdapter().getItem(position);
                                    categoryId = categoryResponse.getId();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }
                });


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

}