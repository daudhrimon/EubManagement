package com.polok.eubmanagement.presentation.assignment.assignmentadd;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.base.model.OnNavigate;
import com.polok.eubmanagement.databinding.FragmentNoticeAddBinding;
import com.polok.eubmanagement.presentation.notice.noticeadd.NoticeAddViewModel;
import com.polok.eubmanagement.util.SharedPref;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class AssignmentAddFragment extends BaseFragment<FragmentNoticeAddBinding> {
    @Override
    protected FragmentNoticeAddBinding initViewBinding() {
        return FragmentNoticeAddBinding.inflate(getLayoutInflater());
    }
    NoticeAddViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this).get(NoticeAddViewModel.class);
    }
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

        binding.saveNoticeButton.setOnClickListener(view -> {
            SharedPref.init(getContext());
            viewModel.validateNoticeInputAndUploadToFirebase(
                    binding.noticeTitle, binding.noticeDetails
            );
        });
    }
    @Override
    protected void onNavigateEvent(OnNavigate onNavigate) {
        super.onNavigateEvent(onNavigate);
        if (onNavigate.getId() == 1) getActivity().onBackPressed();
    }
    @Override
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}
}
