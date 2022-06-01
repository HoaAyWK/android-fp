package com.hoavy.orapp.api;

import android.content.Context;

import com.hoavy.orapp.auth.TokenAuthenticator;
import com.hoavy.orapp.auth.TokenInterceptor;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitAuthClient {
    private static Retrofit retrofit = null;
    private RetrofitAuthClient() { }

    public static Retrofit getInstance(String baseUrl, Context context) {
        if (retrofit == null) {
            TokenAuthenticator authenticator = new TokenAuthenticator(context);
            TokenInterceptor interceptor = new TokenInterceptor(context);
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .authenticator(authenticator)
                    .addInterceptor(interceptor)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
