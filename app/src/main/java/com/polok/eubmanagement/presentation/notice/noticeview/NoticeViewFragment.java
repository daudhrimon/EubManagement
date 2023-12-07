package com.polok.eubmanagement.presentation.notice.noticeview;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentNoticeViewBinding;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class NoticeViewFragment extends BaseFragment<FragmentNoticeViewBinding> {
    @Override
    protected FragmentNoticeViewBinding initViewBinding() {
        return FragmentNoticeViewBinding.inflate(getLayoutInflater());
    }
    NoticeViewViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this).get(NoticeViewViewModel.class);
    }
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

        viewModel.fetchNoticeFromBundle(getArguments().getString("notice_data"));

        viewModel.getNoticeLiveData().observe(getViewLifecycleOwner(), noticeData -> {
            if (noticeData != null) {
                binding.createdAt.setText(noticeData.getNotNullText(noticeData.getCreatedAt()));
                binding.noticeTitle.setText(noticeData.getNotNullText(noticeData.getTitle()));
                binding.noticeDetails.setText(noticeData.getNotNullText(noticeData.getDetails()));
            }
        });
    }
    @Override
    protected PrimaryLoader initPrimaryLoader() {return null;}
}