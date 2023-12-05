package com.polok.eubmanagement.presentation.home.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentStudentBinding;
import com.polok.eubmanagement.presentation.notice.NoticeActivity;

public class StudentFragment extends BaseFragment<FragmentStudentBinding> {
    @Override
    protected FragmentStudentBinding initViewBinding() {
        return FragmentStudentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected BaseViewModel setViewModel() {
        return null;
    }

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

        binding.viewAllButton.setOnClickListener(view -> {
            openNoticeActivity();
        });
        binding.noticeButton.setOnClickListener(view -> {
            openNoticeActivity();
            Log.wtf("ajkahjka","");
        });
    }

    private void openNoticeActivity() {
        startActivity(new Intent(getContext(), NoticeActivity.class));
    }
}