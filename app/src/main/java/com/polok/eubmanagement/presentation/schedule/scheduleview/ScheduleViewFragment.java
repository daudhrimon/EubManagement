package com.polok.eubmanagement.presentation.schedule.scheduleview;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentScheduleViewBinding;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class ScheduleViewFragment extends BaseFragment<FragmentScheduleViewBinding> {
    @Override
    protected FragmentScheduleViewBinding initViewBinding() {
        return FragmentScheduleViewBinding.inflate(getLayoutInflater());
    }
    ScheduleViewViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this).get(ScheduleViewViewModel.class);
    }
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

        viewModel.fetchNoticeFromBundle(getArguments().getString("notice_data"));

        viewModel.getNoticeLiveData().observe(getViewLifecycleOwner(), noticeData -> {
            if (noticeData != null) {
                binding.createdAt.setText(String.format("Created At: %s", noticeData.getNotNullText(noticeData.getCreatedAt())));
                binding.noticeTitle.setText(noticeData.getNotNullText(noticeData.getTitle()));
                binding.noticeDetails.setText(noticeData.getNotNullText(noticeData.getDetails()));
            }
        });
    }
    @Override
    protected PrimaryLoader initPrimaryLoader() {return null;}
}
