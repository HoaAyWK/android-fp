package com.hoavy.orapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tokens {

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
    private Object errors;
    @SerializedName("createdDate")
    @Expose
    private Object createdDate;
    @SerializedName("firstName")
    @Expose
    private Object firstName;
    @SerializedName("lastName")
    @Expose
    private Object lastName;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("phone")
    @Expose
    private Object phone;
    @SerializedName("dateOfBirth")
    @Expose
    private Object dateOfBirth;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("country")
    @Expose
    private Object country;
    @SerializedName("featuredAvatar")
    @Expose
    private Object featuredAvatar;
    @SerializedName("featuredBackground")
    @Expose
    private Object featuredBackground;

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

    public Object getErrors() {
        return errors;
    }

    public void setErrors(Object errors) {
        this.errors = errors;
    }

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getPhone() {
        return phone;
    }

    public void setPhone(Object phone) {
        this.phone = phone;
    }

    public Object getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Object dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public Object getCountry() {
        return country;
    }

    public void setCountry(Object country) {
        this.country = country;
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

}

