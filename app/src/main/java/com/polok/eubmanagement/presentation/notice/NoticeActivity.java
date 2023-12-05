package com.polok.eubmanagement.presentation.notice;

import android.os.Bundle;
import com.polok.eubmanagement.base.BaseActivity;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.ActivityNoticeBinding;

public class NoticeActivity extends BaseActivity<ActivityNoticeBinding> {
    @Override
    protected ActivityNoticeBinding initViewBinding() {
        return ActivityNoticeBinding.inflate(getLayoutInflater());
    }

    @Override
    protected BaseViewModel setViewModel() {return null;}

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        binding.toolBar.backButton.setOnClickListener(view -> {
            onBackPressed();
        });
    }
}
