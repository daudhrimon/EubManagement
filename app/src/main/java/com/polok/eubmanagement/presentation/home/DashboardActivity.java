package com.polok.eubmanagement.presentation.home;

import android.os.Bundle;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseActivity;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.ActivityDashboardBinding;

import java.util.Objects;

public class DashboardActivity extends BaseActivity<ActivityDashboardBinding> {
    @Override
    protected ActivityDashboardBinding initViewBinding() {
        return ActivityDashboardBinding.inflate(getLayoutInflater());
    }
    private NavController navController;

    @Override
    protected BaseViewModel setViewModel() {
        return null;
    }

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

        navController = Navigation.findNavController(this, R.id.fragment_container);

        setIntendedNavGraph(getIntent().getStringExtra("is_admin"));

        NavigationUI.setupWithNavController(binding.bottomNav, navController);
    }

    private void setIntendedNavGraph(String destination) {

        if (Objects.equals(destination, "true")) {
            navController.setGraph(navController.getNavInflater().inflate(R.navigation.nav_graph_admin));
        } else {
            navController.setGraph(navController.getNavInflater().inflate(R.navigation.nav_graph_student));
        }
    }
}