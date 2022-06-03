package com.hoavy.orapp.models.dtos;

public class AvatarRequest {
    private String filePath;
    private String userId;

    public AvatarRequest(String filePath, String userId) {
        this.filePath = filePath;
        this.userId = userId;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
