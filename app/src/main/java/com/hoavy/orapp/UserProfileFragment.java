package com.hoavy.orapp;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hoavy.orapp.databinding.UserProfileFragmentBinding;
import com.hoavy.orapp.utils.SharedHelper;

public class UserProfileFragment extends Fragment {

    private UserProfileViewModel userProfileViewModel;
    private UserProfileFragmentBinding binding;
    SharedHelper sharedHelper;

    public static UserProfileFragment newInstance() {
        return new UserProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.user_profile_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userProfileViewModel = new ViewModelProvider(this).get(UserProfileViewModel.class);
    }

}