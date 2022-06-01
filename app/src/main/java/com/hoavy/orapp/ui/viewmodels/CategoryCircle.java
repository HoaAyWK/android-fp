package com.hoavy.orapp.ui.viewmodels;

public class CategoryCircle {
    private String mId;
    private String mImage;
    private String mName;

    public CategoryCircle(String id, String mImage, String mName) {
        this.mId = id;
        this.mImage = mImage;
        this.mName = mName;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }
}
