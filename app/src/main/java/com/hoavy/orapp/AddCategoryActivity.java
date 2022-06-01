package com.hoavy.orapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.auth.TokenAuthenticator;
import com.hoavy.orapp.databinding.ActivityAddCategoryBinding;
import com.hoavy.orapp.models.dtos.CategoryRequest;
import com.hoavy.orapp.utils.SharedHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddCategoryActivity extends AppCompatActivity {
    private RetrofitAPI mRetrofitAPI;
    private SharedHelper sharedHelper;

    ActivityAddCategoryBinding binding;
    Uri imageUri;
    StorageReference storageRef;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);
        sharedHelper = SharedHelper.getInstance(this);
        mRetrofitAPI = ApiUtils.getAuthAPIService(this);

        binding = ActivityAddCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSelectCateImgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage();
            }
        });

        binding.btnSaveCateAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
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
                        binding.imageAddCategory.setImageURI(null);
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }

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
                                        binding.etCateAddDescription.setText("");
                                        binding.etCateAddName.setText("");
                                        Toast.makeText(AddCategoryActivity.this,"Added new category", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<String> call, Throwable t) {
                                        Toast.makeText(AddCategoryActivity.this,"Cannot add category", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(AddCategoryActivity.this,"Cannot add category due to", Toast.LENGTH_SHORT).show();
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
}