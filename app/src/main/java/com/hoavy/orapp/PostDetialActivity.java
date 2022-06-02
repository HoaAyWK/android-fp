package com.hoavy.orapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.hoavy.orapp.ui.postdetail.ListFreelancerFragment;
import com.hoavy.orapp.ui.postdetail.PostDetialFragment;
import com.hoavy.orapp.ui.postdetail.UpdatePostFragment;

public class PostDetialActivity extends AppCompatActivity {
    String postId = "";
    private boolean updatedPost = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_detial_activity);
        Bundle bundle = getIntent().getBundleExtra("post");
        String id = bundle.getString("id");
        postId = id;
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.post_detail_container, PostDetialFragment.newInstance(id))
                    .commitNow();
        }
    }

    public void addUpdatePostFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.post_detail_container, UpdatePostFragment.newInstance())
                .commit();
    }

    public void addPostDetailFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.post_detail_container, PostDetialFragment.newInstance(postId))
                .commit();
    }

    public void addFreelancerListFragment() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.post_detail_container, ListFreelancerFragment.newInstance(postId))
                .commit();
    }


    public void handleUpdatedPost() {
        this.updatedPost = true;
    }

    public boolean getUpdatedPost() {
        return this.updatedPost;
    }
}