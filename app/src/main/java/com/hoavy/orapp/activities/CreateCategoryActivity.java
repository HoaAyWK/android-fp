package com.hoavy.orapp.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hoavy.orapp.AddCategoryActivity;
import com.hoavy.orapp.R;
import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.databinding.ActivityCreateCategoryBinding;
import com.hoavy.orapp.models.dtos.CategoryRequest;
import com.hoavy.orapp.utils.SharedHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateCategoryActivity extends AppCompatActivity {

    private RetrofitAPI mRetrofitAPI;

    ActivityCreateCategoryBinding binding;
    Uri imageUri;
    StorageReference storageRef;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("Create Category");

        mRetrofitAPI = ApiUtils.getAuthAPIService(this);

        binding = ActivityCreateCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSelectCateImgAdd.setOnClickListener(v -> selectedImage());

        binding.btnSaveCateAdd.setOnClickListener(v -> uploadImage());

        binding.btnCancelAddCate.setOnClickListener(v -> {
            finish();
        });
    }

    private void selectedImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    private void uploadImage() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating category");
        progressDialog.show();

        String fileName = imageUri.getPath().substring(imageUri.getPath().lastIndexOf("/")+1);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        storageRef = storageReference.child("images/categories/" + fileName + ".jpg");
        storageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imgLink = uri.toString();
                                String cateName = binding.etCateAddName.getText().toString();
                                String cateDescription = binding.etCateAddDescription.getText().toString();

                                CategoryRequest category = new CategoryRequest(cateName, cateDescription, imgLink);
                                mRetrofitAPI.addCategory(category).enqueue(new Callback<String>() {
                                    @Override
                                    public void onResponse(Call<String> call, Response<String> response) {
                                        if (response.body() != null) {
                                            if (progressDialog.isShowing()) {
                                                progressDialog.dismiss();
                                            }
                                            Toast.makeText(CreateCategoryActivity.this, "Added new category", Toast.LENGTH_SHORT).show();
                                            setResult(150);
                                            finish();
                                        }
                                        if (response.code() != 200) {
                                            if (progressDialog.isShowing()) {
                                                progressDialog.dismiss();
                                            }
                                            Toast.makeText(CreateCategoryActivity.this, "Cannot add category", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        if (progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }
                                        Toast.makeText(CreateCategoryActivity.this,"Cannot add category", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(CreateCategoryActivity.this,"Cannot add category", Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null) {
            imageUri = data.getData();
            binding.imageAddCategory.setImageURI(imageUri);
        }
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