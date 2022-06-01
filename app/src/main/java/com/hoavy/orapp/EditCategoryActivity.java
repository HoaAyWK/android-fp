package com.hoavy.orapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.hoavy.orapp.databinding.ActivityEditCategoryBinding;

public class EditCategoryActivity extends AppCompatActivity {
    ActivityEditCategoryBinding binding;
    Uri imageUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_category);

        binding = ActivityEditCategoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnSelectCategoryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage();
            }
        });

        binding.btnSaveEditCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });
    }

    private void uploadImage() {

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
}