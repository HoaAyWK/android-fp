package com.hoavy.orapp.models.dtos.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CategoriesResponse {
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("resultCount")
    @Expose
    private Integer resultCount;
    @SerializedName("resultPerPage")
    @Expose
    private Integer resultPerPage;
    @SerializedName("content")
    @Expose
    private List<CategoryResponse> content = null;
    @SerializedName("error")
    @Expose
    private Object error;
    @SerializedName("isSuccess")
    @Expose
    private Boolean isSuccess;
    @SerializedName("responseTime")
    @Expose
    private String responseTime;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public Integer getResultPerPage() {
        return resultPerPage;
    }

    public void setResultPerPage(Integer resultPerPage) {
        this.resultPerPage = resultPerPage;
    }

    public List<CategoryResponse> getContent() {
        return content;
    }

    public void setContent(List<CategoryResponse> content) {
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
