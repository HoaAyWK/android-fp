package com.hoavy.orapp.models;

import androidx.annotation.Nullable;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("userAvatar")
    @Expose
    private String userAvatar;
    @SerializedName("roles")
    @Expose
    private List<String> roles = null;
    @SerializedName("userId")
    @Expose
    private String userId;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("refreshToken")
    @Expose
    private String refreshToken;
    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("errors")
    @Expose
    private List<String> errors;
    @SerializedName("createdDate")
    @Expose
    private String createdDate;
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
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("featuredAvatar")
    @Expose
    private String featuredAvatar;
    @SerializedName("featuredBackground")
    @Expose
    private String featuredBackground;

    @Nullable
    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(@Nullable String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    @Nullable
    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(@Nullable List<String> errors) {
        this.errors = errors;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    @Nullable
    public String getAddress() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    @Nullable
    public String getCountry() {
        return country;
    }

    public void setCountry(@Nullable String country) {
        this.country = country;
    }

    @Nullable
    public String getFeaturedAvatar() {
        return featuredAvatar;
    }

    public void setFeaturedAvatar(@Nullable String featuredAvatar) {
        this.featuredAvatar = featuredAvatar;
    }

    @Nullable
    public String getFeaturedBackground() {
        return featuredBackground;
    }

    public void setFeaturedBackground(@Nullable String featuredBackground) {
        this.featuredBackground = featuredBackground;
    }

}