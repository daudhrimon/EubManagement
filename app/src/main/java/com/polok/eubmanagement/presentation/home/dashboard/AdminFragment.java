package com.polok.eubmanagement.presentation.home.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentAdminBinding;
import com.polok.eubmanagement.databinding.FragmentStudentBinding;
import com.polok.eubmanagement.presentation.notice.NoticeActivity;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class AdminFragment extends BaseFragment<FragmentAdminBinding> {
    @Override
    protected FragmentAdminBinding initViewBinding() {
        return FragmentAdminBinding.inflate(getLayoutInflater());
    }

    @Override
    protected BaseViewModel initViewModel() {
        return null;
    }

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

        binding.viewAllButton.setOnClickListener(view -> {
            openNoticeActivity();
        });
        binding.noticeButton.setOnClickListener(view -> {
            openNoticeActivity();
        });
    }

    @Override
    protected PrimaryLoader initPrimaryLoader() {return null;}

    private void openNoticeActivity() {
        startActivity(new Intent(getContext(), NoticeActivity.class));
    }
}