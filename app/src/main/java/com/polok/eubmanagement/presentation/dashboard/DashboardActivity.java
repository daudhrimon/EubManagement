package com.polok.eubmanagement.presentation.dashboard;

import android.os.Bundle;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseActivity;
import com.polok.eubmanagement.databinding.ActivityDashboardBinding;

public class DashboardActivity extends BaseActivity<ActivityDashboardBinding> {
    @Override
    protected ActivityDashboardBinding initViewBinding() {
        return ActivityDashboardBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        NavigationUI.setupWithNavController(
                binding.bottomNav,
                Navigation.findNavController(this, R.id.fragment_container)
        );
    }
}