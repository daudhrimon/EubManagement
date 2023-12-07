package com.polok.eubmanagement.presentation.assignment.assignmentview;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProvider;

import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentAssignmentViewBinding;
import com.polok.eubmanagement.databinding.FragmentNoticeViewBinding;
import com.polok.eubmanagement.presentation.notice.noticeview.NoticeViewViewModel;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class AssignmentViewFragment extends BaseFragment<FragmentAssignmentViewBinding> {
    @Override
    protected FragmentAssignmentViewBinding initViewBinding() {
        return FragmentAssignmentViewBinding.inflate(getLayoutInflater());
    }
    AssignmentViewViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this).get(AssignmentViewViewModel.class);
    }
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

        viewModel.fetchAssignmentFromBundle(getArguments().getString("assignment_data"));

        viewModel.getAssignmentLiveData().observe(getViewLifecycleOwner(), assignmentData -> {
            if (assignmentData != null) {
                binding.createdAt.setText(String.format("Created At: %s", assignmentData.getNotNullText(assignmentData.getCreatedAt())));
                binding.assignmentTitle.setText(assignmentData.getNotNullText(assignmentData.getTitle()));
                binding.assignmentDetails.setText(assignmentData.getNotNullText(assignmentData.getDetails()));
            }
        });
    }
    @Override
    protected PrimaryLoader initPrimaryLoader() {return null;}
}

