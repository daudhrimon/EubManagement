package com.polok.eubmanagement.presentation.notice.addnotice;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentAddNoticeBinding;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class AddNoticeFragment extends BaseFragment<FragmentAddNoticeBinding> {
    @Override
    protected FragmentAddNoticeBinding initViewBinding() {
        return FragmentAddNoticeBinding.inflate(getLayoutInflater());
    }
    AddNoticeViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this).get(AddNoticeViewModel.class);
    }

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {


        binding.saveNoticeButton.setOnClickListener(view -> {
            viewModel.validateNoticeInputAndUploadToFirebase(
                    binding.noticeTitle, binding.noticeDetails
            );
        });

        viewModel.getUploadNoticeResponse().observe(getViewLifecycleOwner(), isUploaded -> {
            if (isUploaded != null && isUploaded) getActivity().onBackPressed();
        });
    }

    @Override
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}
}
