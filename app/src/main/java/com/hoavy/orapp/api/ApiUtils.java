package com.hoavy.orapp.api;

import android.content.Context;

public class ApiUtils {
    private ApiUtils() { }
    public static final String BASE_URL = "https://open-request-api.herokuapp.com/api/v1/";
    public static RetrofitAPI getAPIService() {
        return RetrofitClient.getClient(BASE_URL).create(RetrofitAPI.class);
    }
    public static RetrofitAPI getAuthAPIService(Context context) {
        return RetrofitAuthClient.getInstance(BASE_URL, context).create(RetrofitAPI.class);
    }
}
