package com.hoavy.orapp.models;

import java.util.List;

public class Post {
    private String title;
    private String description;
    private String featuredImage;
    private double price;
    private double duration;
    private List<String> categories;

    public Post(String title, String description, String featuredImage, double price, double duration, List<String> categories) {
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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public List<String> getCategories() {
        return categories;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }
}
