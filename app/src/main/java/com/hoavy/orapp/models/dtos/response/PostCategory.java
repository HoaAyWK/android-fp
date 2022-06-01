package com.hoavy.orapp.models.dtos.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PostCategory {
    @SerializedName("postId")
    @Expose
    private String postId;
    @SerializedName("categoryId")
    @Expose
    private String categoryId;
    @SerializedName("category")
    @Expose
    private Category category;

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

}
