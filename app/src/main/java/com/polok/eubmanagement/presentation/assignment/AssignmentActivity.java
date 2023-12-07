package com.polok.eubmanagement.presentation.assignment;

import android.os.Bundle;
import com.polok.eubmanagement.base.BaseActivity;
import com.polok.eubmanagement.databinding.ActivityAssignmentBinding;

public class AssignmentActivity extends BaseActivity<ActivityAssignmentBinding> {
    @Override
    protected ActivityAssignmentBinding initViewBinding() {
        return ActivityAssignmentBinding.inflate(getLayoutInflater());
    }
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        binding.toolBar.backButton.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}
