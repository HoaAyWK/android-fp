package com.hoavy.orapp.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hoavy.orapp.BottomNavigationActivity;
import com.hoavy.orapp.CreatePostActivity;
import com.hoavy.orapp.LoginActivity;
import com.hoavy.orapp.R;
import com.hoavy.orapp.UserProfileFragment;
import com.hoavy.orapp.adapters.PostAdapter;
import com.hoavy.orapp.adapters.PostCardAdapter;
import com.hoavy.orapp.databinding.FragmentProfileBinding;
import com.hoavy.orapp.models.dtos.response.PostsResponse;
import com.hoavy.orapp.utils.SharedHelper;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    ProfileViewModel profileViewModel;
    SharedHelper sharedHelper;
    PostAdapter availablePostsAdapter;
    PostAdapter processingPostsAdapter;
    PostAdapter finishedPostAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileViewModel = new ViewModelProvider(requireActivity())
                .get(ProfileViewModel.class);
        profileViewModel.init(getContext());
        sharedHelper = SharedHelper.getInstance(getContext());
        availablePostsAdapter = new PostAdapter(getContext());
        processingPostsAdapter = new PostAdapter(getContext());
        finishedPostAdapter = new PostAdapter(getContext());

        if (sharedHelper.isLoggedIn()) {
            String name = sharedHelper.getFistName() + " " + sharedHelper.getLastName();
            profileViewModel.setName(name);

            if (sharedHelper.getAvatar() != null) {
                profileViewModel.setmImage(sharedHelper.getAvatar());
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);

        TextView titleProcessingPosts = binding.titleProcessingPosts;
        TextView titleFinishedPosts = binding.titleFinishedPosts;
        TextView titleLatestPosts = binding.titleLatestPosts;

        profileViewModel.getAvailablePostResponses().observe(getViewLifecycleOwner(), new Observer<PostsResponse>() {
            @Override
            public void onChanged(PostsResponse postsResponse) {
                if (postsResponse != null) {
                    availablePostsAdapter.setPostResponses(postsResponse.getContent());
                    titleLatestPosts.setText(R.string.latest_posts);
                }
            }
        });

        profileViewModel.getProcessingPostResponses().observe(getViewLifecycleOwner(), new Observer<PostsResponse>() {
            @Override
            public void onChanged(PostsResponse postsResponse) {
                if (postsResponse != null) {
                    processingPostsAdapter.setPostResponses(postsResponse.getContent());
                    titleProcessingPosts.setText(R.string.processing_posts);
                }
            }
        });

        profileViewModel.getFinishedPostResponses().observe(getViewLifecycleOwner(), new Observer<PostsResponse>() {
            @Override
            public void onChanged(PostsResponse postsResponse) {
                if (postsResponse != null) {
                    finishedPostAdapter.setPostResponses(postsResponse.getContent());
                    titleFinishedPosts.setText(R.string.finished_posts);
                }
            }
        });

        View root = binding.getRoot();
        LinearLayout profilePanel = binding.profilePanel;
        RelativeLayout signInOrSignUp = binding.signInOrSignUp;
        ImageView profileAvatar = binding.imgProfileAvatar;
        TextView profileName = binding.profileName;
        Button btnSignOut = binding.btnSignOut;
        Button btnSignIn = binding.btnSignIn;
        Button btnCreateNewPost = binding.createNewPost;

        if (sharedHelper.isLoggedIn()) {
            profilePanel.setVisibility(View.VISIBLE);
            signInOrSignUp.setVisibility(View.INVISIBLE);
        } else {
            profilePanel.setVisibility(View.INVISIBLE);
            signInOrSignUp.setVisibility(View.VISIBLE);
        }

        RecyclerView recyclerAvailablePosts = binding.recyclerCustomerPosts;
        recyclerAvailablePosts.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerAvailablePosts.setAdapter(availablePostsAdapter);



            RecyclerView recyclerProcessingPosts = binding.recyclerProcessingPosts;
            recyclerProcessingPosts.setLayoutManager(new LinearLayoutManager(container.getContext(),
                    LinearLayoutManager.HORIZONTAL, false));
            recyclerProcessingPosts.setAdapter(processingPostsAdapter);


            RecyclerView recyclerFinishedPosts = binding.recyclerFinishedPosts;
            recyclerFinishedPosts.setLayoutManager(new LinearLayoutManager(container.getContext(),
                    LinearLayoutManager.HORIZONTAL, false));
            recyclerFinishedPosts.setAdapter(finishedPostAdapter);

        if (profileViewModel.getImageLiveData().getValue() != null && !profileViewModel.getImageLiveData().getValue().isEmpty()) {
            Glide.with(ProfileFragment.this)
                    .load(profileViewModel.getImageLiveData().getValue())
                    .into(profileAvatar);
        }

        profileViewModel.getImageLiveData().observe(getViewLifecycleOwner(),
                new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        if (s != null && !s.isEmpty()) {
                            Glide.with(ProfileFragment.this)
                                    .load(s)
                                    .into(profileAvatar);
                        }
                    }
                });
        profileViewModel.getNameLiveData().observe(getViewLifecycleOwner(),
                new Observer<String>() {
                    @Override
                    public void onChanged(String s) {
                        profileName.setText(s);
                    }
                });

        btnCreateNewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CreatePostActivity.class);
                startActivity(intent);

            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedHelper.logout();
                profileViewModel.setName("");
                profileViewModel.setmImage(null);
                profilePanel.setVisibility(View.INVISIBLE);
                signInOrSignUp.setVisibility(View.VISIBLE);
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}