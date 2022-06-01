package com.hoavy.orapp.models.dtos.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersResponse {
    @SerializedName("content")
    @Expose
    private List<UserResponse> content = null;
    @SerializedName("error")
    @Expose
    private Object error;
    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("responseTime")
    @Expose
    private String responseTime;

    public List<UserResponse> getContent() {
        return content;
    }

    public void setContent(List<UserResponse> content) {
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
