package com.hoavy.orapp.ui.postdetail;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hoavy.orapp.R;
import com.hoavy.orapp.databinding.PostDetailFragmentBinding;
import com.hoavy.orapp.models.dtos.response.PostResponse;
import com.hoavy.orapp.models.dtos.response.SinglePostResponse;
import com.hoavy.orapp.utils.SharedHelper;

public class PostDetialFragment extends Fragment {
    private SharedHelper sharedHelper;
    private PostDetailViewModel postDetailViewModel;
    private PostDetailFragmentBinding binding;
    private String postId;
    private PostResponse post;
    public PostDetialFragment(String id) {
        postId = id;
        sharedHelper = SharedHelper.getInstance(getContext());
    }
    public static PostDetialFragment newInstance(String id) {
        return new PostDetialFragment(id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postDetailViewModel = new ViewModelProvider(this)
                .get(PostDetailViewModel.class);
        postDetailViewModel.init(postId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding =  PostDetailFragmentBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        ImageView imgPost = binding.imgPostDetails;
        ImageView ownerAvatar = binding.imgOwnerPostAvatar;

        TextView postTitle = binding.titlePostDetail;
        TextView postDescription = binding.descriptionPostDetails;
        TextView postPrice = binding.pricePostDetail;
        TextView postDuration = binding.durationPostDetails;
        TextView ownerName = binding.ownerPostName;
        TextView ownerEmail = binding.ownerPostEmail;
        TextView ownerPhone = binding.ownerPostPhone;

        ImageButton btnEdit = binding.btnEditPost;
        Button btnSelect = binding.selectPostDetails;

        CardView cardOwnerPost = binding.cardOwnerPost;

        postDetailViewModel.getPostResponseLiveData().observe(getViewLifecycleOwner(),
                new Observer<SinglePostResponse>() {
                    @Override
                    public void onChanged(SinglePostResponse postResponse) {
                        if (postResponse != null) {
                            post = postResponse.getContent();
                            postTitle.setText(post.getTitle());
                            postDescription.setText(post.getDescription());

                            String price = "$" + String.valueOf(post.getPrice());
                            postPrice.setText(price);

                            Glide.with(PostDetialFragment.this)
                                    .load(post.getFeaturedImage())
                                    .into(imgPost);

                            if (post.getAuthor().getFeaturedAvatar() != null) {
                                Glide.with(PostDetialFragment.this)
                                        .load(post.getAuthor().getFeaturedAvatar())
                                        .into(ownerAvatar);
                            }

                            String duration = "";
                            if (post.getDuration() > 1 ) {
                                duration = String.valueOf(post.getDuration()) + " days";
                            } else {
                                duration = String.valueOf(post.getDuration()) + " day";
                            }
                            postDuration.setText(duration);

                            String authorName = post.getAuthor().getFirstName() + " " + post.getAuthor().getLastName();
                            ownerName.setText(authorName);
                            ownerEmail.setText(post.getAuthor().getEmail());

                            if (post.getAuthor().getPhone() != null) {
                                ownerPhone.setText(post.getAuthor().getPhone());
                            }

                            if (TextUtils.equals(sharedHelper.getId(), post.getAuthorId())) {
                                btnEdit.setVisibility(View.VISIBLE);
                                btnSelect.setVisibility(View.INVISIBLE);
                                cardOwnerPost.setVisibility(View.INVISIBLE);
                            } else {
                                btnEdit.setVisibility(View.INVISIBLE);
                                btnSelect.setVisibility(View.VISIBLE);
                            }

                            btnEdit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                        }
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