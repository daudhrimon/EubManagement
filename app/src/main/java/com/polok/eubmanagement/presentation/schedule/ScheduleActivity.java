package com.polok.eubmanagement.presentation.schedule;

import android.os.Bundle;
import com.polok.eubmanagement.base.BaseActivity;
import com.polok.eubmanagement.databinding.ActivityScheduleBinding;

public class ScheduleActivity extends BaseActivity<ActivityScheduleBinding> {
    @Override
    protected ActivityScheduleBinding initViewBinding() {
        return ActivityScheduleBinding.inflate(getLayoutInflater());
    }
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        binding.toolBar.backButton.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}
