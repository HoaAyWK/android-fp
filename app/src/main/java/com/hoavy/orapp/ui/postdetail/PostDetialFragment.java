package com.hoavy.orapp.ui.postdetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hoavy.orapp.PostDetialActivity;
import com.hoavy.orapp.R;
import com.hoavy.orapp.adapters.FreelancerRequestAdapter;
import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.databinding.PostDetailFragmentBinding;
import com.hoavy.orapp.models.dtos.response.PostRequest;
import com.hoavy.orapp.models.dtos.response.PostResponse;
import com.hoavy.orapp.models.dtos.response.SelectResponse;
import com.hoavy.orapp.models.dtos.response.SinglePostResponse;
import com.hoavy.orapp.utils.SharedHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetialFragment extends Fragment {
    private SharedHelper sharedHelper;
    private PostDetailViewModel postDetailViewModel;
    private PostDetailFragmentBinding binding;
    private String postId;
    private PostResponse post;
    private RetrofitAPI mRetrofitAPIAuth;

    public PostDetialFragment(String id) {
        postId = id;
        sharedHelper = SharedHelper.getInstance(getContext());
        if (TextUtils.equals(sharedHelper.getRole(), "Freelancer")) {
            mRetrofitAPIAuth = ApiUtils.getAuthAPIService(getContext());
        }
    }
    public static PostDetialFragment newInstance(String id) {
        return new PostDetialFragment(id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        getActivity().setTitle("Post Detail");

        postDetailViewModel = new ViewModelProvider(requireActivity())
                .get(PostDetailViewModel.class);
        postDetailViewModel.init(postId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding =  PostDetailFragmentBinding.inflate(inflater, container, false);

        View root = binding.getRoot();
        binding.skeleton.setVisibility(View.INVISIBLE);
        binding.freelancerRequestsTitle.setVisibility(View.INVISIBLE);
        binding.panelSelectedFreelancer.setVisibility(View.INVISIBLE);

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
                                cardOwnerPost.setVisibility(View.GONE);

                                if (post.getPostRequests().size() > 0) {
                                    binding.freelancerRequestsTitle.setVisibility(View.VISIBLE);

                                    binding.freelancerRequestsTitle.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            if (getActivity() != null) {
                                                ((PostDetialActivity) getActivity()).addFreelancerListFragment();
                                            }
                                        }
                                    });
                                } else {
                                    binding.freelancerRequestsTitle.setVisibility(View.GONE);
                                }

                                if (post.getFreelancer() != null) {
                                    binding.freelancerRequestsTitle.setVisibility(View.GONE);
                                    binding.panelSelectedFreelancer.setVisibility(View.VISIBLE);

                                    String freelancerName = post.getFreelancer().getFirstName()
                                            + " " + post.getFreelancer().getLastName();
                                    binding.freelancerName.setText(freelancerName);
                                    binding.freelancerEmail.setText(post.getFreelancer().getEmail());

                                    if (post.getFreelancer().getPhone() != null) {
                                        binding.freelancerPhone.setText(post.getFreelancer().getPhone());
                                    }
                                } else {
                                    binding.panelSelectedFreelancer.setVisibility(View.GONE);
                                }
                            } else {
                                btnEdit.setVisibility(View.INVISIBLE);
                                btnSelect.setVisibility(View.VISIBLE);
                                binding.freelancerRequestsTitle.setVisibility(View.GONE);
                                binding.panelSelectedFreelancer.setVisibility(View.GONE);
                            }

                            if (TextUtils.equals(sharedHelper.getRole(), "Freelancer")) {
                                btnSelect.setVisibility(View.VISIBLE);
                                for (PostRequest request : post.getPostRequests()) {
                                    if (TextUtils.equals(sharedHelper.getId(), request.getFreelancerId())) {
                                        btnSelect.setText(R.string.pending);
                                    }
                                }
                            } else {
                                btnSelect.setVisibility(View.GONE);
                            }

                            btnEdit.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onClickEdit();
                                }
                            });

                            btnSelect.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    onSelectedPost();
                                }
                            });

                            binding.skeleton.setVisibility(View.VISIBLE);
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

   public void onClickEdit() {
        if (getActivity() != null) {
            ((PostDetialActivity)getActivity()).addUpdatePostFragment();
        }
   }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getActivity() != null) {
                    if (((PostDetialActivity) getActivity()).getUpdatedPost()) {
                        ((PostDetialActivity) getActivity()).setResult(110);
                    }
                    ((PostDetialActivity) getActivity()).finish();
                }
                return true;
        }
        return false;
    }

    public void onSelectedPost() {
        Button selectButton = binding.selectPostDetails;
        if (TextUtils.equals(selectButton.getText(), "Select")) {
            mRetrofitAPIAuth.selectPost(postId).enqueue(new Callback<SelectResponse>() {
                @Override
                public void onResponse(Call<SelectResponse> call, Response<SelectResponse> response) {
                    if (response.body() != null) {
                        selectButton.setText(R.string.pending);
                        Toast.makeText(requireContext(), "Selected post", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SelectResponse> call, Throwable t) {
                    Toast.makeText(requireContext(), "Cannot select this post", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            String userId = sharedHelper.getId();
            mRetrofitAPIAuth.unselectPost(postId, userId).enqueue(new Callback<SelectResponse>() {
                @Override
                public void onResponse(Call<SelectResponse> call, Response<SelectResponse> response) {
                    if (response.body() != null) {
                        selectButton.setText(R.string.select);
                        Toast.makeText(requireContext(), "Unselected post", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<SelectResponse> call, Throwable t) {
                    Toast.makeText(requireContext(), "Cannot unselect this post", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}