package com.hoavy.orapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.hoavy.orapp.ui.postdetail.PostDetialFragment;

public class PostDetialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detial_activity);
        Bundle bundle = getIntent().getBundleExtra("post");
        String id = bundle.getString("id");
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, PostDetialFragment.newInstance(id))
                    .commitNow();
        }
    }
}