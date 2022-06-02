package com.hoavy.orapp.ui.postdetail;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hoavy.orapp.CreatePostActivity;
import com.hoavy.orapp.PostDetialActivity;
import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.databinding.FragmentUpdatePostBinding;
import com.hoavy.orapp.models.Post;
import com.hoavy.orapp.models.dtos.PostRequest;
import com.hoavy.orapp.models.dtos.response.CategoriesResponse;
import com.hoavy.orapp.models.dtos.response.CategoryResponse;
import com.hoavy.orapp.models.dtos.response.PostResponse;
import com.hoavy.orapp.models.dtos.response.SinglePostResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePostFragment extends Fragment {

    private RetrofitAPI mRetrofitAPI;
    private RetrofitAPI mRetrofitAPIAuth;
    PostDetailViewModel postDetailViewModel;
    ArrayAdapter<CategoryResponse> categoryAdapter;

    FragmentUpdatePostBinding binding;

    Uri imageUri;
    StorageReference storageRef;
    ProgressDialog progressDialog;

    String categoryId = "";

    public UpdatePostFragment() {
        // Required empty public constructor
    }

    public static UpdatePostFragment newInstance() {
        return new UpdatePostFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        postDetailViewModel = new ViewModelProvider(requireActivity())
                .get(PostDetailViewModel.class);
        mRetrofitAPI = ApiUtils.getAPIService();
        mRetrofitAPIAuth = ApiUtils.getAuthAPIService(requireContext());

        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUpdatePostBinding.inflate(inflater, container, false);

        View root = binding.getRoot();

        mRetrofitAPI.getCategories().enqueue(new Callback<CategoriesResponse>() {
            @Override
            public void onResponse(Call<CategoriesResponse> call, Response<CategoriesResponse> response) {
                if (response.body() != null) {
                    categoryAdapter = new ArrayAdapter<CategoryResponse>(requireContext(),
                            android.R.layout.simple_spinner_item, response.body().getContent());
                    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.listCategoryUp.setAdapter(categoryAdapter);
                    binding.listCategoryUp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            CategoryResponse categoryResponse = (CategoryResponse) parent.getAdapter().getItem(position);
                            categoryId = categoryResponse.getId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    postDetailViewModel.getPostResponseLiveData().observe(getViewLifecycleOwner(), new Observer<SinglePostResponse>() {
                        @Override
                        public void onChanged(SinglePostResponse singlePostResponse) {
                            PostResponse post = singlePostResponse.getContent();
                            binding.postTitleUp.setText(post.getTitle());
                            binding.postDescriptionUp.setText(post.getDescription());
                            binding.postPriceUp.setText(post.getPrice().toString());
                            binding.postDurationUp.setText(post.getDescription());
                            Glide.with(UpdatePostFragment.this)
                                    .load(post.getFeaturedImage())
                                    .into(binding.selectedPostImg);

                            int count = categoryAdapter.getCount();
                            for (int i = 0; i < count; i++) {
                                if (TextUtils.equals(categoryAdapter.getItem(i).getId(), post.getPostCategories().get(0).getCategoryId())) {
                                    binding.listCategoryUp.setSelection(i);
                                }
                            }

                            binding.btnSave.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    String title = binding.postTitleUp.getText().toString();
                                    String description = binding.postDescriptionUp.getText().toString();
                                    String price = binding.postPriceUp.getText().toString();
                                    String duration = binding.postPriceUp.getText().toString();
                                    String category = categoryId;

                                    if (TextUtils.isEmpty(title)) {
                                        Toast.makeText(requireContext(), "Please fill title field", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if (TextUtils.isEmpty(description)) {
                                        Toast.makeText(requireContext(), "Please fill description field", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if (TextUtils.isEmpty(price)) {
                                        Toast.makeText(requireContext(), "Please fill price field", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    if (TextUtils.isEmpty(duration)) {
                                        Toast.makeText(requireContext(), "Please fill duration field", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                    uploadImageAndUpdatePost(post.getId(), title, description,
                                            post.getFeaturedImage(), price, duration, category);
                                }
                            });

                        }
                    });
                    categoryAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CategoriesResponse> call, Throwable t) {

            }
        });

        binding.btnSelectPostImgUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedImage();
            }
        });


        binding.btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        return root;
    }

    private void selectedImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    private void uploadImageAndUpdatePost(String postId, String title, String description, String featuredImage,
                                          String price, String duration, String category) {
        progressDialog = new ProgressDialog(requireContext());
        progressDialog.setTitle("Uploading Category Image");
        progressDialog.show();

        if (imageUri != null) {
            String fileName = imageUri.getPath().substring(imageUri.getPath().lastIndexOf("/") + 1);
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storageReference = storage.getReference();
            storageRef = storageReference.child("images/categories/" + fileName + ".jpg");
            storageRef.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            binding.selectedPostImg.setImageURI(null);
                            if (progressDialog.isShowing()) {
                                progressDialog.dismiss();
                            }

                            storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String imgLink = uri.toString();
                                    List<String> categories = new ArrayList<>();
                                    categories.add(category);

                                    PostRequest postRequest = new PostRequest(title, description, imgLink, duration, price, categories);

                                    mRetrofitAPIAuth.updatePost(postId, postRequest).enqueue(new Callback<Post>() {
                                        @Override
                                        public void onResponse(Call<Post> call, Response<Post> response) {
                                            if (response.body() != null) {
                                                if (progressDialog.isShowing()) {
                                                    progressDialog.dismiss();
                                                }
                                                postDetailViewModel.getPost(postId);
                                                onBackPressed();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Post> call, Throwable t) {
                                            Toast.makeText(requireContext(), "Cannot Create Post", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(requireContext(), "Cannot create post due to", Toast.LENGTH_SHORT).show();
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
        } else {
            List<String> categories = new ArrayList<>();
            categories.add(category);

            PostRequest postRequest = new PostRequest(title, description, featuredImage, duration, price, categories);

            mRetrofitAPIAuth.updatePost(postId, postRequest).enqueue(new Callback<Post>() {
                @Override
                public void onResponse(Call<Post> call, Response<Post> response) {
                    if (response.body() != null) {
                        if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        postDetailViewModel.getPost(postId);
                        onBackPressed();
                    }
                }

                @Override
                public void onFailure(Call<Post> call, Throwable t) {
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    Toast.makeText(requireContext(), "Cannot Create Post", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return false;
    }

    public void onBackPressed() {
        ((PostDetialActivity)getActivity()).addPostDetailFragment();
    }
}