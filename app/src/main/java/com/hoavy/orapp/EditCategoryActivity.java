package com.hoavy.orapp;

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

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hoavy.orapp.activities.CreateCategoryActivity;
import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.databinding.ActivityEditCategoryBinding;
import com.hoavy.orapp.models.dtos.CategoryRequest;
import com.hoavy.orapp.models.dtos.response.UpdateCateResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditCategoryActivity extends AppCompatActivity {
    ActivityEditCategoryBinding binding;
    Uri imageUri;
    StorageReference storageRef;
    ProgressDialog progressDialog;
    RetrofitAPI mRetrofitAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        setTitle("Update Category");
        mRetrofitAPI = ApiUtils.getAuthAPIService(this);

        Bundle bundle = getIntent().getBundleExtra("category");
        String id = bundle.getString("id");
        String name = bundle.getString("name");
        String description = bundle.getString("description");
        String image = bundle.getString("image");

        binding = ActivityEditCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.etCategoryName.setText(name);
        binding.etDescription.setText(description);

        Glide.with(this)
                .load(image)
                .into(binding.imgEditCategory);

        binding.btnSelectCategoryImg.setOnClickListener(v -> selectedImage());

        binding.btnSaveEditCategory.setOnClickListener(v -> uploadImageAndCategory(id));
    }

    private void uploadImageAndCategory(String id) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating category");
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
                                String cateName = binding.etCategoryName.getText().toString();
                                String cateDescription = binding.etDescription.getText().toString();

                                CategoryRequest category = new CategoryRequest(cateName, cateDescription, imgLink);
                                mRetrofitAPI.updateCategory(id, category).enqueue(new Callback<UpdateCateResponse>() {
                                    @Override
                                    public void onResponse(Call<UpdateCateResponse> call, Response<UpdateCateResponse> response) {
                                        if (response.body() != null) {
                                            if (progressDialog.isShowing()) {
                                                progressDialog.dismiss();
                                            }
                                            Toast.makeText(EditCategoryActivity.this, "Updated category", Toast.LENGTH_SHORT).show();
                                            setResult(170);
                                            finish();
                                        }
                                        if (response.code() != 200) {
                                            if (progressDialog.isShowing()) {
                                                progressDialog.dismiss();
                                            }
                                            Toast.makeText(EditCategoryActivity.this, "Cannot update category", Toast.LENGTH_SHORT).show();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<UpdateCateResponse> call, Throwable t) {
                                        if (progressDialog.isShowing()) {
                                            progressDialog.dismiss();
                                        }
                                        Toast.makeText(EditCategoryActivity.this,"Cannot update category", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                if (progressDialog.isShowing()) {
                                    progressDialog.dismiss();
                                }
                                Toast.makeText(EditCategoryActivity.this,"Cannot update category", Toast.LENGTH_SHORT).show();
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
            binding.imgEditCategory.setImageURI(imageUri);
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