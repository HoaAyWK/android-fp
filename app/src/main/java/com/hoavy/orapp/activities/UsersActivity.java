package com.hoavy.orapp.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.faltenreich.skeletonlayout.Skeleton;
import com.faltenreich.skeletonlayout.SkeletonLayoutUtils;
import com.hoavy.orapp.R;
import com.hoavy.orapp.adapters.CustomerAdapter;
import com.hoavy.orapp.api.ApiUtils;
import com.hoavy.orapp.api.RetrofitAPI;
import com.hoavy.orapp.databinding.ActivityUsersBinding;
import com.hoavy.orapp.models.dtos.response.UsersResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersActivity extends AppCompatActivity {

    ActivityUsersBinding binding;
    RetrofitAPI mRetrofitAPIAuth;

    CustomerAdapter customerAdapter;
    CustomerAdapter freelancerAdapter;

    RecyclerView recyclerFreelancers;
    RecyclerView recyclerCustomers;

    Skeleton skeletonFreelancers;
    Skeleton skeletonCustomers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRetrofitAPIAuth = ApiUtils.getAuthAPIService(this);
        customerAdapter = new CustomerAdapter(this);
        freelancerAdapter = new CustomerAdapter(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("Manage Users");

        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        recyclerCustomers = binding.recyclerCustomers;
        recyclerCustomers.setLayoutManager(new LinearLayoutManager(this));
        skeletonCustomers = SkeletonLayoutUtils.applySkeleton(recyclerCustomers, R.layout.item_list_user);
        skeletonCustomers.showSkeleton();

        recyclerFreelancers = binding.recyclerFreelancers;
        recyclerFreelancers.setLayoutManager(new LinearLayoutManager(this));
        skeletonFreelancers = SkeletonLayoutUtils.applySkeleton(recyclerFreelancers, R.layout.item_list_user);
        skeletonFreelancers.showSkeleton();

        loadCustomers();
        loadFreelancers();

    }

    public void loadCustomers() {
        mRetrofitAPIAuth.getAllCustomers().enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                if (response.body() != null) {
                    skeletonCustomers.showOriginal();
                    customerAdapter.setUserResponses(response.body().getContent());
                    recyclerCustomers.setAdapter(customerAdapter);
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {

            }
        });
    }

    public void loadFreelancers() {
        mRetrofitAPIAuth.getAllFreelancers().enqueue(new Callback<UsersResponse>() {
            @Override
            public void onResponse(Call<UsersResponse> call, Response<UsersResponse> response) {
                if (response.body() != null) {
                    skeletonFreelancers.showOriginal();
                    freelancerAdapter.setUserResponses(response.body().getContent());
                    recyclerFreelancers.setAdapter(freelancerAdapter);
                }
            }

            @Override
            public void onFailure(Call<UsersResponse> call, Throwable t) {

            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.switch_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                finish();
                break;
            case R.id.switch_view:
                supportInvalidateOptionsMenu();
                boolean isSwitchedCustomer = customerAdapter.toggleItemViewType();
                recyclerCustomers.setLayoutManager(isSwitchedCustomer ? new LinearLayoutManager(this) : new GridLayoutManager(this, 3));
                recyclerCustomers.setAdapter(customerAdapter);

                boolean isSwitchedFreelancer = freelancerAdapter.toggleItemViewType();
                recyclerFreelancers.setLayoutManager(isSwitchedFreelancer ? new LinearLayoutManager(this) : new GridLayoutManager(this, 3));
                recyclerFreelancers.setAdapter(freelancerAdapter);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}