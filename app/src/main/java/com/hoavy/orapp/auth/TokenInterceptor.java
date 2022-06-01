package com.hoavy.orapp.auth;

import android.content.Context;

import androidx.annotation.NonNull;

import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.models.Tokens;
import com.hoavy.orapp.models.dtos.TokensRequest;
import com.hoavy.orapp.utils.SharedHelper;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    SharedHelper sharedHelper;
    RetrofitAPI mRetrofitAPI;
    TokensRequest newTokens = null;

    public TokenInterceptor(Context context) {
        mRetrofitAPI = ApiUtils.getAPIService();
        sharedHelper = SharedHelper.getInstance(context);
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request newRequest = chain.request();
        long expireTime = TimeUnit.MINUTES.toMillis(120);
        long storedDate = sharedHelper.getStoredDate();
        Calendar calendar = Calendar.getInstance();
        Date nowDate = calendar.getTime();
        Date expireDate = new Date(storedDate + expireTime);

        int result = nowDate.compareTo(expireDate);

        if (result > 0) {
            boolean gotNewTokens = getNewTokens();
            if (gotNewTokens) {
                newRequest = chain.request()
                        .newBuilder()
                        .header("Authorization", "Bearer " + newTokens.getToken())
                        .build();
            }
        }
        return chain.proceed(newRequest);
    }

    private boolean getNewTokens() throws IOException {
        String token = sharedHelper.getToken();
        String refreshToken = sharedHelper.getRefreshToken();
        TokensRequest tokensRequest = new TokensRequest(token, refreshToken);
        retrofit2.Response<Tokens> response = mRetrofitAPI.refreshTokens(tokensRequest).execute();
        if (response.body() != null) {
            Tokens tokensResponse = response.body();
            sharedHelper.setToken(tokensResponse.getToken());
            sharedHelper.setRefreshToken(tokensResponse.getRefreshToken());
            sharedHelper.setStoredDate(System.currentTimeMillis());
            newTokens = new TokensRequest(tokensResponse.getToken(),
                    tokensResponse.getRefreshToken());
            return true;
        } else {
            newTokens = null;
            return false;
        }
    }
}
