package com.hoavy.orapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.databinding.FragmentRegisterBinding;
import com.hoavy.orapp.models.User;
import com.hoavy.orapp.models.dtos.RegisterRequest;
import com.hoavy.orapp.utils.SharedHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterFragment extends Fragment {

    FragmentRegisterBinding binding;
    SharedHelper sharedHelper;
    ArrayAdapter<String> adapterRoles;
    RetrofitAPI mRetrofitAPI;

    String role = "Freelancer";

    public RegisterFragment() {
        sharedHelper = SharedHelper.getInstance(getContext());
        mRetrofitAPI = ApiUtils.getAPIService();
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        getActivity().setTitle("Register");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        Spinner registerAs = binding.spinnerRoles;
        TextView errorMessage = binding.registerIncorrect;
        EditText etFirstName = binding.etRegisterFirstName;
        EditText etLastName = binding.etRegisterLastName;
        EditText etEmail = binding.etRegisterEmail;
        EditText etPassword = binding.etRegisterPassword;
        EditText etRepeatPassword = binding.etRepeatPassword;
        Button registerButton = binding.btnRegister2;
        Button signInButton = binding.btnSignIn2;

        List<String> roles = new ArrayList<>();
        roles.add("Freelancer");
        roles.add("Customer");

        adapterRoles = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, roles);
        adapterRoles.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        registerAs.setAdapter(adapterRoles);
        registerAs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                role = (String) parent.getAdapter().getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        registerButton.setClickable(false);

        registerButton.setOnClickListener(v -> {
            String message = "";
            String firstName = etFirstName.getText().toString();
            String lastName = etLastName.getText().toString();
            String email = etEmail.getText().toString();
            String password = etPassword.getText().toString();
            String repeatPassword = etRepeatPassword.getText().toString();

            if (!TextUtils.equals(password, repeatPassword)) {
                message = "Repeat password does not match";
                errorMessage.setText(message);
                errorMessage.setVisibility(View.VISIBLE);
                return;
            }

            RegisterRequest request = new RegisterRequest(firstName, lastName, email, password);
            if (TextUtils.equals(role, "Freelancer")) {
                mRetrofitAPI.freelancerRegister(request).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.body() != null) {
                            errorMessage.setVisibility(View.INVISIBLE);
                            User registerResponse = response.body();
                            long currentDate = System.currentTimeMillis();
                            sharedHelper.setUserInfo(registerResponse.getUserId(), registerResponse.getToken(),
                                    registerResponse.getRefreshToken(), registerResponse.getEmail(),
                                    registerResponse.getFirstName(), registerResponse.getLastName(),
                                    registerResponse.getUserAvatar(), registerResponse.getFeaturedBackground(),
                                    registerResponse.getPhone(), registerResponse.getAddress(), registerResponse.getCountry(),
                                    registerResponse.getRoles().get(0), currentDate);

                            if (getActivity() != null) {
                                ((LoginActivity) getActivity()).onLoginCompleted();
                            }
                        }

                        if (response.code() != 200) {
                            errorMessage.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            } else {
                mRetrofitAPI.customerRegister(request).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.code() == 200) {
                            errorMessage.setVisibility(View.INVISIBLE);
                            User registerResponse = response.body();
                            long currentDate = System.currentTimeMillis();
                            sharedHelper.setUserInfo(registerResponse.getUserId(), registerResponse.getToken(),
                                    registerResponse.getRefreshToken(), registerResponse.getEmail(),
                                    registerResponse.getFirstName(), registerResponse.getLastName(),
                                    registerResponse.getUserAvatar(), registerResponse.getFeaturedBackground(),
                                    registerResponse.getPhone(), registerResponse.getAddress(), registerResponse.getCountry(),
                                    registerResponse.getRoles().get(0), currentDate);

                            if (getActivity() != null) {
                                ((LoginActivity) getActivity()).onLoginCompleted();
                            }
                        } else {
                            errorMessage.setVisibility(View.VISIBLE);

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
            }
        });

        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getActivity() != null) {
                    onBackPressed();
                }
                return true;
        }
        return false;
    }

    public void onBackPressed() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}