package com.hoavy.orapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.hoavy.orapp.ui.create_new_post.CreateNewPostFragment;

public class CreateNewPostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_post_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, CreateNewPostFragment.newInstance())
                    .commitNow();
        }
    }
}