package com.hoavy.orapp;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.models.User;
import com.hoavy.orapp.models.dtos.LoginRequest;
import com.hoavy.orapp.utils.SharedHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private EditText etEmail, etPassword;
    private Button btnLogin;
    private TextView incorrect;

    private RetrofitAPI mRetrofitAPI;
    private SharedHelper sharedHelper;

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(@Nullable View view, @Nullable Bundle saveInstanceState) {
        super.onViewCreated(view, saveInstanceState);

        sharedHelper = SharedHelper.getInstance(getContext());
        mRetrofitAPI = ApiUtils.getAPIService();

        getActivity().setTitle(R.string.sign_in_to_your_account);

        etEmail = view.findViewById(R.id.et_login_email);
        etPassword = view.findViewById(R.id.et_login_password);
        incorrect = view.findViewById(R.id.tv_login_incorrect);
        btnLogin = view.findViewById(R.id.btn_login);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incorrect.setText("");
                String email = etEmail.getText().toString();
                String password = etPassword.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {
                    startSignIn(email, password);
                }
            }
        });
    }

    public void startSignIn(String email, String password) {
        if (getActivity() != null) {
            LoginRequest loginRequest = new LoginRequest(email, password);
            postLogin(loginRequest);
        }
    }

    public void postLogin(LoginRequest loginRequest) {
        mRetrofitAPI.login(loginRequest).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.body() != null) {
                    User loginResponse = response.body();
                    long currentDate = System.currentTimeMillis();
                    sharedHelper.setUserInfo(loginResponse.getUserId(), loginResponse.getToken(),
                            loginResponse.getRefreshToken(), loginResponse.getEmail(),
                            loginResponse.getFirstName(), loginResponse.getLastName(),
                            loginResponse.getUserAvatar(), loginResponse.getFeaturedBackground(),
                            loginResponse.getPhone(), loginResponse.getAddress(),
                            loginResponse.getCountry(), loginResponse.getRoles().get(0), currentDate);

                    ((LoginActivity)getActivity()).onLoginCompleted();
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("ErrorInLoginMethod", t.toString());
            }
        });

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }
}