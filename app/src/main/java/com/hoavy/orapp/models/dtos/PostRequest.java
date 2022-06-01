package com.hoavy.orapp.models.dtos;

import java.util.List;

public class PostRequest {
    private String title;
    private String description;
    private String featuredImage;
    private String price;
    private String duration;
    private List<String> categories;

    public PostRequest(String title, String description, String featuredImage, String price, String duration, List<String> categories) {
        this.title = title;
        this.description = description;
        this.featuredImage = featuredImage;
        this.price = price;
        this.duration = duration;
        this.categories = categories;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
