package com.hoavy.orapp.ui.viewmodels;

public class CategoryCardViewModel {
    private String mName;
    private String mImage;

    public CategoryCardViewModel(String mName, String mImage) {
        this.mName = mName;
        this.mImage = mImage;
    }


    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }
}
