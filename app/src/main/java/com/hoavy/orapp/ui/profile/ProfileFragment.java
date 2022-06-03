package com.hoavy.orapp.ui.profile;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;
import com.hoavy.orapp.BottomNavigationActivity;
import com.hoavy.orapp.CreatePostActivity;
import com.hoavy.orapp.LoginActivity;
import com.hoavy.orapp.R;
import com.hoavy.orapp.UserProfileFragment;
import com.hoavy.orapp.activities.UpdateProfileActivity;
import com.hoavy.orapp.adapters.PostAdapter;
import com.hoavy.orapp.adapters.PostCardAdapter;
import com.hoavy.orapp.databinding.FragmentProfileBinding;
import com.hoavy.orapp.models.dtos.UpdateInfoRequest;
import com.hoavy.orapp.models.dtos.response.PostsResponse;
import com.hoavy.orapp.ui.home.HomeViewModel;
import com.hoavy.orapp.utils.SharedHelper;

public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;
    private Skeleton skeletonLatestPosts;
    private Skeleton skeletonProcessingPosts;
    private Skeleton skeletonFinishedPosts;
    private Skeleton skeletonFreelancerProcessingPosts;
    private Skeleton skeletonTitleLatest;
    private Skeleton skeletonTitleProcessing;
    private Skeleton skeletonTitleFinished;
    private Skeleton skeletonTitleFreelancerProcessingPosts;

    ProfileViewModel profileViewModel;
    HomeViewModel homeViewModel;
    SharedHelper sharedHelper;
    PostAdapter availablePostsAdapter;
    PostAdapter processingPostsAdapter;
    PostAdapter finishedPostAdapter;
    PostAdapter freelancerProcessingPostsAdapter;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 99 || result.getResultCode() == 110) {
                        reloadPosts();
                    }

                    if (result.getResultCode() == 202) {
                        reloadAvatarAndInfo();
                    }
                }
            }
    );

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedHelper = SharedHelper.getInstance(getContext());
        profileViewModel = new ViewModelProvider(requireActivity())
                .get(ProfileViewModel.class);
        homeViewModel = new ViewModelProvider(requireActivity())
                .get(HomeViewModel.class);

        if (TextUtils.equals(sharedHelper.getRole(), "Customer")) {

            profileViewModel.init(getContext(), sharedHelper.getRole());

            availablePostsAdapter = new PostAdapter(getContext(), activityResultLauncher);
            processingPostsAdapter = new PostAdapter(getContext(), activityResultLauncher);
            finishedPostAdapter = new PostAdapter(getContext(), activityResultLauncher);
        } else if (TextUtils.equals(sharedHelper.getRole(), "Freelancer")) {
            profileViewModel.init(getContext(), sharedHelper.getRole());
            freelancerProcessingPostsAdapter = new PostAdapter(getContext(), activityResultLauncher);
        }

        if (sharedHelper.isLoggedIn()) {
            String name = sharedHelper.getFistName() + " " + sharedHelper.getLastName();
            profileViewModel.setName(name);

            if (sharedHelper.getAvatar() != null && !TextUtils.isEmpty(sharedHelper.getAvatar())) {
                profileViewModel.setmImage(sharedHelper.getAvatar());
            }
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        String email = "Email: " + sharedHelper.getEmail();
        String phone = "Phone: " + sharedHelper.getPhone();
        String address = "Address: " + sharedHelper.getAddress();
        String country = "Country: " + sharedHelper.getCountry();
        String role = "Role: " + sharedHelper.getRole();

        binding.userEmail.setText(email);
        binding.userPhone.setText(phone);
        binding.userAddress.setText(address);
        binding.userRole.setText(role);
        binding.userCountry.setText(country);

        if (TextUtils.equals(sharedHelper.getRole(), "Freelancer")) {
            binding.customerPostsPanel.setVisibility(View.GONE);
            TextView titleFreelancerProcessingPosts = binding.titleFreelancerProcessingPosts;
            skeletonTitleFreelancerProcessingPosts = SkeletonLayoutUtils.createSkeleton(titleFreelancerProcessingPosts);
            skeletonTitleFreelancerProcessingPosts.showSkeleton();

            RecyclerView recyclerFreelancerPosts = binding.recyclerFreelancerPosts;
            recyclerFreelancerPosts.setLayoutManager(new LinearLayoutManager(container.getContext(),
                    LinearLayoutManager.HORIZONTAL, false));
            recyclerFreelancerPosts.setHasFixedSize(true);

            skeletonFreelancerProcessingPosts = SkeletonLayoutUtils.applySkeleton(recyclerFreelancerPosts, R.layout.item_latest_post);
            skeletonFreelancerProcessingPosts.showSkeleton();

            profileViewModel.getFreelancerProcessingPostResponse().observe(getViewLifecycleOwner(), new Observer<PostsResponse>() {
                @Override
                public void onChanged(PostsResponse postsResponse) {
                    if (postsResponse != null) {
                        skeletonFreelancerProcessingPosts.showOriginal();
                        freelancerProcessingPostsAdapter.setPostResponses(postsResponse.getContent());
                        skeletonTitleFreelancerProcessingPosts.showOriginal();
                        if (postsResponse.getContent().size() > 0) {
                            titleFreelancerProcessingPosts.setText(R.string.processing_posts);
                        }
                        recyclerFreelancerPosts.setAdapter(freelancerProcessingPostsAdapter);
                    }
                }
            });

        } else if (TextUtils.equals(sharedHelper.getRole(), "Customer"))  {
            binding.freelancerPostsPanel.setVisibility(View.GONE);
            TextView titleProcessingPosts = binding.titleProcessingPosts;
            TextView titleFinishedPosts = binding.titleFinishedPosts;
            TextView titleLatestPosts = binding.titleLatestPosts;

            skeletonTitleLatest = SkeletonLayoutUtils.createSkeleton(titleLatestPosts);
            skeletonTitleProcessing = SkeletonLayoutUtils.createSkeleton(titleProcessingPosts);
            skeletonTitleFinished = SkeletonLayoutUtils.createSkeleton(titleFinishedPosts);

            skeletonTitleLatest.showSkeleton();
            skeletonTitleProcessing.showSkeleton();
            skeletonTitleFinished.showSkeleton();

            RecyclerView recyclerAvailablePosts = binding.recyclerCustomerPosts;
            recyclerAvailablePosts.setLayoutManager(new LinearLayoutManager(container.getContext(),
                    LinearLayoutManager.HORIZONTAL, false));

            skeletonLatestPosts = SkeletonLayoutUtils.applySkeleton(recyclerAvailablePosts, R.layout.item_latest_post);
            skeletonLatestPosts.showSkeleton();

            profileViewModel.getAvailablePostResponses().observe(getViewLifecycleOwner(), new Observer<PostsResponse>() {
                @Override
                public void onChanged(PostsResponse postsResponse) {
                    if (postsResponse != null) {
                        skeletonLatestPosts.showOriginal();
                        availablePostsAdapter.setPostResponses(postsResponse.getContent());
                        skeletonTitleLatest.showOriginal();
                        titleLatestPosts.setText(R.string.latest_posts);
                        recyclerAvailablePosts.setAdapter(availablePostsAdapter);
                    }
                }
            });

            RecyclerView recyclerProcessingPosts = binding.recyclerProcessingPosts;
            recyclerProcessingPosts.setLayoutManager(new LinearLayoutManager(container.getContext(),
                    LinearLayoutManager.HORIZONTAL, false));
            skeletonProcessingPosts = SkeletonLayoutUtils.applySkeleton(recyclerProcessingPosts, R.layout.item_latest_post);
            skeletonProcessingPosts.showSkeleton();
            profileViewModel.getProcessingPostResponses().observe(getViewLifecycleOwner(), new Observer<PostsResponse>() {
                @Override
                public void onChanged(PostsResponse postsResponse) {
                    if (postsResponse != null) {
                        skeletonProcessingPosts.showOriginal();
                        processingPostsAdapter.setPostResponses(postsResponse.getContent());
                        skeletonTitleProcessing.showOriginal();
                        if (postsResponse.getContent().size() > 0) {
                            titleProcessingPosts.setText(R.string.processing_posts);
                        }

                        recyclerProcessingPosts.setAdapter(processingPostsAdapter);
                    }
                }
            });

            RecyclerView recyclerFinishedPosts = binding.recyclerFinishedPosts;
            recyclerFinishedPosts.setLayoutManager(new LinearLayoutManager(container.getContext(),
                    LinearLayoutManager.HORIZONTAL, false));
            skeletonFinishedPosts = SkeletonLayoutUtils.applySkeleton(recyclerFinishedPosts, R.layout.item_latest_post);
            skeletonFinishedPosts.showSkeleton();

            profileViewModel.getFinishedPostResponses().observe(getViewLifecycleOwner(), new Observer<PostsResponse>() {
                @Override
                public void onChanged(PostsResponse postsResponse) {
                    if (postsResponse != null) {
                        skeletonFinishedPosts.showOriginal();
                        finishedPostAdapter.setPostResponses(postsResponse.getContent());
                        skeletonTitleFinished.showOriginal();
                        titleFinishedPosts.setText(R.string.finished_posts);

                        recyclerFinishedPosts.setAdapter(finishedPostAdapter);
                    }
                }
            });

            binding.createNewPost.setOnClickListener(v -> {
                Intent intent = new Intent(getContext(), CreatePostActivity.class);

                activityResultLauncher.launch(intent);
            });

        } else {
            binding.customerPostsPanel.setVisibility(View.GONE);
            binding.freelancerPostsPanel.setVisibility(View.GONE);
        }

        LinearLayout profilePanel = binding.profilePanel;
        RelativeLayout signInOrSignUp = binding.signInOrSignUp;
        ImageView profileAvatar = binding.imgProfileAvatar;
        TextView profileName = binding.profileName;
        ImageButton btnSignOut = binding.btnSignOut;
        Button btnSignIn = binding.btnSignIn;

        if (sharedHelper.isLoggedIn()) {
            profilePanel.setVisibility(View.VISIBLE);
            signInOrSignUp.setVisibility(View.INVISIBLE);
        } else {
            profilePanel.setVisibility(View.INVISIBLE);
            signInOrSignUp.setVisibility(View.VISIBLE);
        }

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

        btnSignOut.setOnClickListener(v -> {
            sharedHelper.logout();
            profileViewModel.setName("");
            profileViewModel.setmImage(null);
            profilePanel.setVisibility(View.INVISIBLE);
            signInOrSignUp.setVisibility(View.VISIBLE);
        });

        btnSignIn.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        });

        binding.editProfile.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), UpdateProfileActivity.class);
            activityResultLauncher.launch(intent);
        });

        return root;
    }

    public void reloadPosts() {
        profileViewModel.getPosts();
        homeViewModel.getPosts();
        homeViewModel.getHighestPricePosts();
    }


    public void reloadAvatarAndInfo() {
        String email = "Email: " + sharedHelper.getEmail();
        String phone = "Phone: " + sharedHelper.getPhone();
        String address = "Address: " + sharedHelper.getAddress();
        String country = "Country: " + sharedHelper.getCountry();
        String role = "Role: " + sharedHelper.getRole();

        binding.userEmail.setText(email);
        binding.userPhone.setText(phone);
        binding.userAddress.setText(address);
        binding.userRole.setText(role);
        binding.userCountry.setText(country);
        Glide.with(this)
                .load(sharedHelper.getAvatar())
                .into(binding.imgProfileAvatar);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}