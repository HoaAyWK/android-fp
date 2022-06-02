package com.hoavy.orapp.models.dtos.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Assignment {
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
    @SerializedName("postId")
    @Expose
    private String postId;
    @SerializedName("endDate")
    @Expose
    private String endDate;
    @SerializedName("filePath")
    @Expose
    private Object filePath;

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

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Object getFilePath() {
        return filePath;
    }

    public void setFilePath(Object filePath) {
        this.filePath = filePath;
    }

}
