package com.hoavy.orapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.databinding.ActivityCreatePostBinding;
import com.hoavy.orapp.models.dtos.CategoryRequest;
import com.hoavy.orapp.models.dtos.PostRequest;
import com.hoavy.orapp.models.dtos.response.CategoriesResponse;
import com.hoavy.orapp.models.dtos.response.CategoryResponse;
import com.hoavy.orapp.models.dtos.response.PostResponse;
import com.hoavy.orapp.repositories.PostsRepository;
import com.hoavy.orapp.utils.SharedHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreatePostActivity extends AppCompatActivity {

    private RetrofitAPI mRetrofitAPI;
    private RetrofitAPI mRetrofitAPIAuth;
    ArrayAdapter<CategoryResponse> categoryAdapter;
    ActivityCreatePostBinding binding;

    Uri imageUri;
    StorageReference storageRef;
    ProgressDialog progressDialog;

    String categoryId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_post);

        mRetrofitAPIAuth = ApiUtils.getAuthAPIService(this);
        mRetrofitAPI = ApiUtils.getAPIService();
        binding = ActivityCreatePostBinding.inflate(getLayoutInflater());

        mRetrofitAPI.getCategories().enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if (response.body() != null) {
                    categoryAdapter = new ArrayAdapter<CategoryResponse>(CreatePostActivity.this,
                            android.R.layout.simple_spinner_item, response.body().getContent());
                    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.listCategoryAdd.setAdapter(categoryAdapter);
                    binding.listCategoryAdd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            CategoryResponse categoryResponse = (CategoryResponse) parent.getAdapter().getItem(position);
                            categoryId = categoryResponse.getId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {

            }
        });

        binding.btnSelectPostImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage();
            }
        });

        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImageAndCreatePost();
            }
        });

        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setContentView(binding.getRoot());
    }


    private void selectedImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    private void uploadImageAndCreatePost() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Category Image");
        progressDialog.show();

        String fileName = imageUri.getPath().substring(imageUri.getPath().lastIndexOf("/")+1);
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference();
        storageRef = storageReference.child("images/categories/" + fileName + ".jpg");
        storageRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        binding.imgPostSelected.setImageURI(null);
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String imgLink = uri.toString();
                                String title = binding.postTitleAdd.getText().toString();
                                String description = binding.postDescriptionAdd.getText().toString();
                                String price = binding.postPriceAdd.getText().toString();
                                String duration = binding.postPriceAdd.getText().toString();
                                String category = categoryId;
                                List<String> categories = new ArrayList<>();
                                categories.add(category);

                                PostRequest postRequest = new PostRequest(title, description, imgLink, duration, price, categories);

                                mRetrofitAPIAuth.createPost(postRequest).enqueue(new Callback<PostResponse>() {
                                    @Override
                                    public void onResponse(Call<PostResponse> call, Response<PostResponse> response) {
                                        if (response.body() != null) {
                                            setResult(99);
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<PostResponse> call, Throwable t) {
                                        Toast.makeText(CreatePostActivity.this, "Cannot Create Post", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(CreatePostActivity.this,"Cannot create post due to", Toast.LENGTH_SHORT).show();
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
            binding.imgPostSelected.setImageURI(imageUri);
        }
    }
}