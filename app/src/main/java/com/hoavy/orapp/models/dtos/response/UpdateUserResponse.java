package com.hoavy.orapp.models.dtos.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hoavy.orapp.models.User;

public class UpdateUserResponse {

    @SerializedName("content")
    @Expose
    private User content;
    @SerializedName("error")
    @Expose
    private Object error;
    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("responseTime")
    @Expose
    private String responseTime;

    public User getContent() {
        return content;
    }

    public void setContent(User content) {
        this.content = content;
    }

    public Object getError() {
        return error;
    }

    public void setError(Object error) {
        this.error = error;
    }

    public Boolean getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(Boolean isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(String responseTime) {
        this.responseTime = responseTime;
    }

}

