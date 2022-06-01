package com.hoavy.orapp.models.dtos;

public class CategoryRequest {
    private String name;
    private String description;
    private String featuredImage;

    public CategoryRequest(String name, String description, String featuredImage) {
        this.name = name;
        this.description = description;
        this.featuredImage = featuredImage;
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
}
