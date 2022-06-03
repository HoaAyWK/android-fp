package com.hoavy.orapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;
import com.hoavy.orapp.AddCategoryActivity;
import com.hoavy.orapp.BottomNavigationActivity;
import com.hoavy.orapp.CategoriesActivity;
import com.hoavy.orapp.R;
import com.hoavy.orapp.activities.PostGridActivity;
import com.hoavy.orapp.activities.UsersActivity;
import com.hoavy.orapp.adapters.CategoryAdapter;
import com.hoavy.orapp.adapters.NewUsersAdapter;
import com.hoavy.orapp.adapters.PostGridAdapter;
import com.hoavy.orapp.databinding.FragmentDashboardBinding;
import com.hoavy.orapp.models.dtos.response.UsersResponse;
import com.hoavy.orapp.ui.viewmodels.CategoryCardViewModel;

import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private NewUsersAdapter adapter;
    DashboardViewModel dashboardViewModel;
    Skeleton skeletonRecentRegisters;

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                }
            });

    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewUsersAdapter();
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        dashboardViewModel.init(getContext());
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerNewUsers;
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        skeletonRecentRegisters = SkeletonLayoutUtils.applySkeleton(recyclerView, R.layout.item_new_user);
        skeletonRecentRegisters.showSkeleton();

        dashboardViewModel.getUsersResponseLiveData().observe(getViewLifecycleOwner(),
                new Observer<UsersResponse>() {
                    @Override
                    public void onChanged(UsersResponse usersResponse) {
                        if (usersResponse != null) {
                            skeletonRecentRegisters.showOriginal();
                            adapter.setUserResponses(usersResponse.getContent());
                            recyclerView.setAdapter(adapter);
                        }
                    }
                });


        final ImageButton btnCategory = binding.dashboardBtnCategories;
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(container.getContext(), CategoriesActivity.class);
                activityResultLauncher.launch(intent);
            }
        });

        binding.dashboardBtnUsers.setOnClickListener(v -> {
            Intent intent = new Intent(container.getContext(), UsersActivity.class);
            activityResultLauncher.launch(intent);
        });

        binding.dashboardBtnPosts.setOnClickListener(v -> {
            Intent intent = new Intent(container.getContext(), PostGridActivity.class);
            activityResultLauncher.launch(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}