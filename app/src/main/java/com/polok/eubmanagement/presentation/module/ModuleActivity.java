package com.polok.eubmanagement.presentation.module;

import android.os.Bundle;
import com.polok.eubmanagement.base.BaseActivity;
import com.polok.eubmanagement.databinding.ActivityModuleBinding;

public class ModuleActivity extends BaseActivity<ActivityModuleBinding> {
    @Override
    protected ActivityModuleBinding initViewBinding() {
        return ActivityModuleBinding.inflate(getLayoutInflater());
    }
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        binding.toolBar.backButton.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}
