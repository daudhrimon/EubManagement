package com.polok.eubmanagement.presentation.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentHomeBinding;
import com.polok.eubmanagement.presentation.assignment.AssignmentActivity;
import com.polok.eubmanagement.presentation.module.ModuleActivity;
import com.polok.eubmanagement.presentation.schedule.ScheduleActivity;
import com.polok.eubmanagement.presentation.notice.NoticeActivity;
import com.polok.eubmanagement.presentation.notice.noticelist.NoticeListAdapter;
import com.polok.eubmanagement.util.SharedPref;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {
    @Override
    protected FragmentHomeBinding initViewBinding() {
        return FragmentHomeBinding.inflate(getLayoutInflater());
    }
    HomeViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
    }
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

        viewModel.fetchUserProfileLiveData();
        viewModel.fetchNoticeListFromFirebase();

        viewModel.getUserProfileDataLiveData().observe(getViewLifecycleOwner(), userProfileData -> {
            if (userProfileData != null) {
                binding.userNameAndBatch.setText(
                        String.format("%s\n%s",
                                userProfileData.getNotNullText(userProfileData.getFullName()),
                                String.format("%s, Section %s",
                                        userProfileData.getNotNullText(SharedPref.getUserBatch()),
                                        userProfileData.getNotNullText(userProfileData.getSection())
                                )
                        )
                );
                if (userProfileData.getAdmin() != null && userProfileData.getAdmin()) {
                    //
                }
            }
        });
        viewModel.getNoticeLiveData().observe(getViewLifecycleOwner(), noticeList-> {
            if (noticeList != null && !noticeList.isEmpty()) {
                binding.recentNoticeLayer.setVisibility(View.VISIBLE);
                binding.recentNoticeRecycler.setAdapter(new NoticeListAdapter(noticeList,null));
            }
        });
        binding.viewAllButton.setOnClickListener(view -> {
            openNoticeActivity();
        });
        binding.classScheduleButton.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), ScheduleActivity.class));
        });
        binding.noticeButton.setOnClickListener(view -> {
            openNoticeActivity();
        });
        binding.assignmentButton.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), AssignmentActivity.class));
        });
        binding.courseModuleButton.setOnClickListener(view -> {
            startActivity(new Intent(getContext(), ModuleActivity.class));
        });
    }
    @Override
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}

    private void openNoticeActivity() {
        startActivity(new Intent(getContext(), NoticeActivity.class));
    }
}