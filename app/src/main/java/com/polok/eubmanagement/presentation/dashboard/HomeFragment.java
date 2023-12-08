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
            }
        });
        viewModel.getNoticeLiveData().observe(getViewLifecycleOwner(), noticeList-> {
            if (noticeList != null && !noticeList.isEmpty()) {
                binding.recentNoticeLayer.setVisibility(View.VISIBLE);
                binding.recentNoticeRecycler.setAdapter(new NoticeListAdapter(noticeList, null));

                binding.viewAllButton.setOnClickListener(view -> {
                    openAnotherActivity(NoticeActivity.class);
                });
            }
        });
        binding.classScheduleButton.setOnClickListener(view -> {
            openAnotherActivity(ScheduleActivity.class);
        });
        binding.noticeButton.setOnClickListener(view -> {
            openAnotherActivity(NoticeActivity.class);
        });
        binding.assignmentButton.setOnClickListener(view -> {
            openAnotherActivity(AssignmentActivity.class);
        });
        binding.courseModuleButton.setOnClickListener(view -> {
            openAnotherActivity(ModuleActivity.class);
        });
    }
    @Override
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}

    private void openAnotherActivity(Class<?> destinationClass) {
        startActivity(new Intent(getContext(), destinationClass));
    }
}