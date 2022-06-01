package com.hoavy.orapp.ui.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hoavy.orapp.AddCategoryActivity;
import com.hoavy.orapp.BottomNavigationActivity;
import com.hoavy.orapp.adapters.CategoryAdapter;
import com.hoavy.orapp.adapters.NewUsersAdapter;
import com.hoavy.orapp.databinding.FragmentDashboardBinding;
import com.hoavy.orapp.models.dtos.response.UsersResponse;
import com.hoavy.orapp.ui.viewmodels.CategoryCardViewModel;

import java.util.List;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private NewUsersAdapter adapter;
    DashboardViewModel dashboardViewModel;

    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new NewUsersAdapter();
        dashboardViewModel = new ViewModelProvider(this).get(DashboardViewModel.class);
        dashboardViewModel.init(getContext());
        dashboardViewModel.getUsersResponseLiveData().observe(this,
                new Observer<UsersResponse>() {
                    @Override
                    public void onChanged(UsersResponse usersResponse) {
                        if (usersResponse != null) {
                            adapter.setUserResponses(usersResponse.getContent());
                        }
                    }
                });
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        RecyclerView recyclerView = binding.recyclerNewUsers;
        recyclerView.setLayoutManager(new LinearLayoutManager(container.getContext(),
                LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        final ImageButton btnCategory = binding.dashboardBtnCategories;
        btnCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(container.getContext(), AddCategoryActivity.class);
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