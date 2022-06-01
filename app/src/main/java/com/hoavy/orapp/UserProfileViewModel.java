package com.hoavy.orapp;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hoavy.orapp.utils.SharedHelper;

public class UserProfileViewModel extends ViewModel {
    private Context mContext;
    private SharedHelper sharedHelper;
    private MutableLiveData<String> mName;
    private MutableLiveData<String> mImage;
    private MutableLiveData<Boolean> isSignedIn;

    public UserProfileViewModel(Context context) {
        mContext = context;
        sharedHelper = SharedHelper.getInstance(context);
        mName = new MutableLiveData<>();
        mImage = new MutableLiveData<>();
        isSignedIn = new MutableLiveData<>();
    }

    public void init() {
        if (sharedHelper.isLoggedIn()) {
            String name = sharedHelper.getFistName() + " " + sharedHelper.getLastName();
            String avatar = sharedHelper.getAvatar();
            mName.setValue(name);
            mImage.setValue(avatar);
            isSignedIn.setValue(true);
        } else {
            isSignedIn.setValue(false);
        }
    }

    public void getName() {
        if (sharedHelper.isLoggedIn()) {
            String name = sharedHelper.getFistName() + " " + sharedHelper.getLastName();
            mName.setValue(name);
        }
    }

    public void getImage() {
        if (sharedHelper.isLoggedIn()) {
            String avatar = sharedHelper.getAvatar();
            mImage.setValue(avatar);
        }
    }

    public LiveData<String> getNameLiveData() {
        return mName;
    }

    public LiveData<String> getImageLiveData() {
        return mImage;
    }

    public LiveData<Boolean> getIsSignedIn() {
        return isSignedIn;
    }
}