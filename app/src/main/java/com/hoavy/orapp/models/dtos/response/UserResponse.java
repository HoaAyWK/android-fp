package com.hoavy.orapp.models.dtos.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserResponse {
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
    @SerializedName("identityId")
    @Expose
    private String identityId;
    @SerializedName("firstName")
    @Expose
    private String firstName;
    @SerializedName("lastName")
    @Expose
    private String lastName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("featuredAvatar")
    @Expose
    private Object featuredAvatar;
    @SerializedName("featuredBackground")
    @Expose
    private Object featuredBackground;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("rated")
    @Expose
    private Double rated;
    @SerializedName("posts")
    @Expose
    private Object posts;

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

    public String getIdentityId() {
        return identityId;
    }

    public void setIdentityId(String identityId) {
        this.identityId = identityId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getFeaturedAvatar() {
        return featuredAvatar;
    }

    public void setFeaturedAvatar(Object featuredAvatar) {
        this.featuredAvatar = featuredAvatar;
    }

    public Object getFeaturedBackground() {
        return featuredBackground;
    }

    public void setFeaturedBackground(Object featuredBackground) {
        this.featuredBackground = featuredBackground;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getRated() {
        return rated;
    }

    public void setRated(Double rated) {
        this.rated = rated;
    }

    public Object getPosts() {
        return posts;
    }

    public void setPosts(Object posts) {
        this.posts = posts;
    }

}
