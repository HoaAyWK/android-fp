package com.hoavy.orapp.auth;

import android.content.Context;

import androidx.annotation.Nullable;

import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.models.Tokens;
import com.hoavy.orapp.models.dtos.TokensRequest;
import com.hoavy.orapp.utils.SharedHelper;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {
    private final SharedHelper sharedHelper;

    public TokenAuthenticator(Context context) {
        sharedHelper = SharedHelper.getInstance(context);
    }
    @Nullable
    @Override
    public synchronized Request authenticate(@Nullable Route route, Response response) throws IOException {
        String token = sharedHelper.getToken();
        return response.request().newBuilder()
                .header("Authorization", "Bearer " + token)
                .build();
    }

//    private boolean getNewTokens() throws IOException {
//        String token = sharedHelper.getToken();
//        String refreshToken = sharedHelper.getRefreshToken();
//        TokensRequest tokensRequest = new TokensRequest(token, refreshToken);
//        retrofit2.Response<Tokens> response = mRetrofitAPI.refreshTokens(tokensRequest).execute();
//        if (response.body() != null) {
//            Tokens tokensResponse = response.body();
//            sharedHelper.setToken(tokensResponse.getToken());
//            sharedHelper.setRefreshToken(tokensResponse.getRefreshToken());
//            newTokens = new TokensRequest(tokensResponse.getToken(),
//                    tokensResponse.getRefreshToken());
//            return true;
//        } else {
//           newTokens = null;
//           return false;
//        }
//    }
}
