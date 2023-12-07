package com.polok.eubmanagement.presentation.notice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.polok.eubmanagement.base.BaseActivity;
import com.polok.eubmanagement.databinding.ActivityNoticeBinding;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class NoticeActivity extends BaseActivity<ActivityNoticeBinding> {
    @Override
    protected ActivityNoticeBinding initViewBinding() {
        return ActivityNoticeBinding.inflate(getLayoutInflater());
    }
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        binding.toolBar.backButton.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}
