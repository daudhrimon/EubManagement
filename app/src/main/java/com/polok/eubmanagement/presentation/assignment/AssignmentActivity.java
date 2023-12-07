package com.polok.eubmanagement.presentation.assignment;

import android.os.Bundle;

import com.polok.eubmanagement.base.BaseActivity;

public class AssignmentActivity extends BaseActivity<com.polok.eubmanagement.databinding.ActivityAssignmentBinding> {
    @Override
    protected com.polok.eubmanagement.databinding.ActivityAssignmentBinding initViewBinding() {
        return com.polok.eubmanagement.databinding.ActivityAssignmentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        binding.toolBar.backButton.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}
