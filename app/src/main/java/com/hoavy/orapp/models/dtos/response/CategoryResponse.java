package com.hoavy.orapp.models.dtos.response;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CategoryResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("featuredImage")
    @Expose
    private String featuredImage;
    @SerializedName("postCategories")
    @Expose
    private Object postCategories;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public void setFeaturedImage(String featuredImage) {
        this.featuredImage = featuredImage;
    }

    public Object getPostCategories() {
        return postCategories;
    }

    public void setPostCategories(Object postCategories) {
        this.postCategories = postCategories;
    }


    @NonNull
    @Override
    public String toString() {
        return this.name;
    }
}
