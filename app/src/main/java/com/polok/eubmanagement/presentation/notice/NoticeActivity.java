package com.polok.eubmanagement.presentation.notice;

import android.os.Bundle;
import androidx.navigation.fragment.NavHostFragment;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseActivity;
import com.polok.eubmanagement.databinding.ActivityNoticeBinding;

public class NoticeActivity extends BaseActivity<ActivityNoticeBinding> {
    @Override
    protected ActivityNoticeBinding initViewBinding() {
        return ActivityNoticeBinding.inflate(getLayoutInflater());
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
