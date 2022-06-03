package com.hoavy.orapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hoavy.orapp.R;
import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.databinding.ActivityUpdateProfileBinding;
import com.hoavy.orapp.models.User;
import com.hoavy.orapp.models.dtos.AvatarRequest;
import com.hoavy.orapp.models.dtos.UpdateInfoRequest;
import com.hoavy.orapp.models.dtos.response.SelectResponse;
import com.hoavy.orapp.models.dtos.response.UpdateUserResponse;
import com.hoavy.orapp.utils.SharedHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {

    private ActivityUpdateProfileBinding binding;
    SharedHelper sharedHelper;
    Uri imageUri;
    StorageReference storageRef;
    ProgressDialog progressDialog;
    RetrofitAPI mRetrofitAPIAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        sharedHelper = SharedHelper.getInstance(this);
        mRetrofitAPIAuth = ApiUtils.getAuthAPIService(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("Update User Info");

        binding = ActivityUpdateProfileBinding.inflate(getLayoutInflater());

        String currAvatar =  sharedHelper.getAvatar();
        String currFirstName = sharedHelper.getFistName();
        String currLastName = sharedHelper.getLastName();
        String currPhone = sharedHelper.getPhone();
        String currAddress = sharedHelper.getAddress();
        String currCountry = sharedHelper.getCountry();


        if (currAvatar != null && !TextUtils.isEmpty(currAvatar)) {
            Glide.with(this)
                    .load(sharedHelper.getAvatar())
                    .into(binding.userAvatar);
        }

        binding.userFirstName.setText(currFirstName);
        binding.userLastName.setText(currLastName);

        binding.userAddress.setText(currAddress);

        binding.userPhone.setText(currPhone);
        binding.userCountry.setText(currCountry);

        binding.cancelUpdate.setOnClickListener(v -> {
            finish();
        });

        binding.saveUpdate.setOnClickListener(v -> {
            updateInfo();
        });

        binding.selectUserAvatar.setOnClickListener(v -> {
            selectedImage();
        });
        setContentView(binding.getRoot());
    }

    private void selectedImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null) {
            imageUri = data.getData();
            progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Updating User Avatar");
            progressDialog.show();

            if (imageUri != null) {
                String fileName = imageUri.getPath().substring(imageUri.getPath().lastIndexOf("/") + 1);
                FirebaseStorage storage = FirebaseStorage.getInstance();
                StorageReference storageReference = storage.getReference();
                storageRef = storageReference.child("images/categories/" + fileName + ".jpg");
                storageRef.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imgLink = uri.toString();
                                        AvatarRequest avatarRequest = new AvatarRequest(imgLink, sharedHelper.getId());

                                        mRetrofitAPIAuth.updateAvatar(avatarRequest).enqueue(new Callback<SelectResponse>() {
                                            @Override
                                            public void onResponse(Call<SelectResponse> call, Response<SelectResponse> response) {
                                                if (response.code() == 200) {
                                                    sharedHelper.setAvatar(imgLink);
                                                    binding.userAvatar.setImageURI(imageUri);
                                                    setResult(202);
                                                    progressDialog.dismiss();
                                                    Toast.makeText(UpdateProfileActivity.this, "Updated user avatar", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(UpdateProfileActivity.this, "Cannot update user avatar", Toast.LENGTH_SHORT).show();
                                                    progressDialog.dismiss();
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<SelectResponse> call, Throwable t) {
                                                progressDialog.dismiss();
                                            }
                                        });
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        progressDialog.dismiss();
                                        Toast.makeText(UpdateProfileActivity.this, "Cannot update image", Toast.LENGTH_SHORT).show();
                                    }
                                });

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                            }
                        });
            }
        }
    }

    private void updateInfo() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating User Info");
        progressDialog.show();

            String firstName = binding.userFirstName.getText().toString();
            String lastName = binding.userLastName.getText().toString();
            String address = binding.userAddress.getText().toString();
            String phone = binding.userPhone.getText().toString();
            String country = binding.userCountry.getText().toString();

            UpdateInfoRequest request = new UpdateInfoRequest(firstName, lastName,
                    phone, address, country);

            mRetrofitAPIAuth.updateUserInfo(sharedHelper.getId(), request)
                    .enqueue(new Callback<UpdateUserResponse>() {
                        @Override
                        public void onResponse(Call<UpdateUserResponse> call, Response<UpdateUserResponse> response) {
                            if (response.body() != null) {
                                User user = response.body().getContent();
                                sharedHelper.setFirstName(user.getFirstName());
                                sharedHelper.setLastName(user.getLastName());
                                sharedHelper.setPhone(user.getPhone());
                                sharedHelper.setAddress(user.getAddress());
                                sharedHelper.setCountry(user.getCountry());
                                sharedHelper.setAvatar(user.getUserAvatar());

                                progressDialog.dismiss();
                                setResult(202);
                                finish();
                            }

                            if (response.code() != 200) {
                                progressDialog.dismiss();
                                Toast.makeText(UpdateProfileActivity.this, "Cannot update", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<UpdateUserResponse> call, Throwable t) {

                        }
                    });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return false;
    }
}