package com.hoavy.orapp.models.dtos.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostResponse {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
    @SerializedName("updatedDate")
    @Expose
    private String updatedDate;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("featuredImage")
    @Expose
    private String featuredImage;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("duration")
    @Expose
    private Double duration;
    @SerializedName("authorId")
    @Expose
    private String authorId;
    @SerializedName("author")
    @Expose
    private Author author;
    @SerializedName("assignment")
    @Expose
    private Object assignment;
    @SerializedName("postCategories")
    @Expose
    private List<PostCategory> postCategories = null;
    @SerializedName("postRequests")
    @Expose
    private Object postRequests;
    @SerializedName("freelancerId")
    @Expose
    private Object freelancerId;
    @SerializedName("freelancer")
    @Expose
    private Object freelancer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Object getAssignment() {
        return assignment;
    }

    public void setAssignment(Object assignment) {
        this.assignment = assignment;
    }

    public List<PostCategory> getPostCategories() {
        return postCategories;
    }

    public void setPostCategories(List<PostCategory> postCategories) {
        this.postCategories = postCategories;
    }

    public Object getPostRequests() {
        return postRequests;
    }

    public void setPostRequests(Object postRequests) {
        this.postRequests = postRequests;
    }

    public Object getFreelancerId() {
        return freelancerId;
    }

    public void setFreelancerId(Object freelancerId) {
        this.freelancerId = freelancerId;
    }

    public Object getFreelancer() {
        return freelancer;
    }

    public void setFreelancer(Object freelancer) {
        this.freelancer = freelancer;
    }
}
