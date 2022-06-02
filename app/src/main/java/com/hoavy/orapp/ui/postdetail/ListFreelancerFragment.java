package com.hoavy.orapp.ui.postdetail;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hoavy.orapp.PostDetialActivity;
import com.hoavy.orapp.R;
import com.hoavy.orapp.adapters.FreelancerRequestAdapter;
import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.databinding.FragmentListFreelancerBinding;
import com.hoavy.orapp.models.dtos.response.Freelancer;
import com.hoavy.orapp.models.dtos.response.PostRequest;
import com.hoavy.orapp.models.dtos.response.SinglePostResponse;
import com.hoavy.orapp.utils.SharedHelper;

import java.util.List;

public class ListFreelancerFragment extends Fragment {

    FragmentListFreelancerBinding binding;
    private RetrofitAPI mRetrofitAPIAuth;
    private PostDetailViewModel postDetailViewModel;
    private String postId;

    FreelancerRequestAdapter freelancerAdapter;

    public ListFreelancerFragment(String id) {
        postId = id;
    }

    public static ListFreelancerFragment newInstance(String id) {
        return new ListFreelancerFragment(id);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        getActivity().setTitle("Freelancer Requests");

        postDetailViewModel = new ViewModelProvider(requireActivity())
                .get(PostDetailViewModel.class);
        postDetailViewModel.init(postId);

        freelancerAdapter = new FreelancerRequestAdapter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListFreelancerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerFreelances = binding.recyclerFreelances;

        postDetailViewModel.getPostResponseLiveData().observe(getViewLifecycleOwner(), new Observer<SinglePostResponse>() {
            @Override
            public void onChanged(SinglePostResponse singlePostResponse) {
                if (singlePostResponse != null) {
                    List<PostRequest> requests = singlePostResponse.getContent().getPostRequests();

                    freelancerAdapter.setPostRequests(requests);
                    recyclerFreelances.setLayoutManager(new LinearLayoutManager(container.getContext(),
                            LinearLayoutManager.VERTICAL, false));
                    recyclerFreelances.setHasFixedSize(true);
                    recyclerFreelances.setAdapter(freelancerAdapter);
                }
            }
        });

        return root;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (getActivity() != null) {
                    ((PostDetialActivity) getActivity()).addPostDetailFragment();
                }
                return true;
        }
        return false;
    }
}