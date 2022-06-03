package com.hoavy.orapp;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.hoavy.orapp.databinding.ActivityBottomNavigationBinding;
import com.hoavy.orapp.utils.SharedHelper;

public class BottomNavigationActivity extends AppCompatActivity {

    private ActivityBottomNavigationBinding binding;
    SharedHelper sharedHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedHelper = SharedHelper.getInstance(this);
        AppBarConfiguration appBarConfiguration;
        NavController navController;


        binding = ActivityBottomNavigationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        if (TextUtils.equals(sharedHelper.getRole(), "Admin")) {
            binding.navViewNormal.setVisibility(View.GONE);
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_category, R.id.navigation_dashboard, R.id.navigation_profile)
                    .build();
            navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_bottom_navigation);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navView, navController);

        } else {
            binding.navView.setVisibility(View.GONE);
            appBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.navigation_home, R.id.navigation_category, R.id.navigation_profile)
                    .build();
            navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_bottom_navigation);
            NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
            NavigationUI.setupWithNavController(binding.navViewNormal, navController);
        }

    }

}