package com.polok.eubmanagement.presentation.notice.addnotice;

import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.base.event.Event;
import com.polok.eubmanagement.databinding.FragmentAddNoticeBinding;

public class AddNoticeFragment extends BaseFragment<FragmentAddNoticeBinding> {
    @Override
    protected FragmentAddNoticeBinding initViewBinding() {
        return FragmentAddNoticeBinding.inflate(getLayoutInflater());
    }
    AddNoticeViewModel viewModel;

    @Override
    protected BaseViewModel setViewModel() {return viewModel;}

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(AddNoticeViewModel.class);

        binding.saveNoticeButton.setOnClickListener(view -> {
            viewModel.validateNoticeInputAndUploadToFirebase(
                    binding.noticeTitle, binding.noticeDetails
            );
        });

        viewModel.getLoadingEvent().observe(getViewLifecycleOwner(), new Event.EventObserver<>( isLoading -> {
            if (isLoading) binding.primaryLoader.setVisibility(View.VISIBLE);
            else binding.primaryLoader.setVisibility(View.GONE);
        }));

        viewModel.getUploadNoticeResponse().observe(getViewLifecycleOwner(), isUploaded -> {
            if (isUploaded != null && isUploaded) getActivity().onBackPressed();
        });
    }
}
