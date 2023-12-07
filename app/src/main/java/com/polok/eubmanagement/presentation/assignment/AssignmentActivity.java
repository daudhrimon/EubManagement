package com.polok.eubmanagement.presentation.assignment;

import android.os.Bundle;
import androidx.navigation.fragment.NavHostFragment;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseActivity;
import com.polok.eubmanagement.databinding.ActivityAssignmentBinding;

public class AssignmentActivity extends BaseActivity<ActivityAssignmentBinding> {
    @Override
    protected ActivityAssignmentBinding initViewBinding() {
        return ActivityAssignmentBinding.inflate(getLayoutInflater());
    }
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        try {
            ((NavHostFragment) getSupportFragmentManager().findFragmentById(
                    R.id.fragment_container
            )).getNavController().addOnDestinationChangedListener((navController, destination, bundle) -> {
                binding.toolBar.title.setText(destination.getLabel());
            });
        } catch (Exception ignored) {}

        binding.toolBar.backButton.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}
