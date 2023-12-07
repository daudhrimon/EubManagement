package com.polok.eubmanagement.presentation.notice.noticeadd;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentNoticeAddBinding;
import com.polok.eubmanagement.util.SharedPref;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class NoticeAddFragment extends BaseFragment<FragmentNoticeAddBinding> {
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
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}
    @Override
    protected void onNavigateEvent(int id, Bundle bundle) {
        super.onNavigateEvent(id, bundle);
        if (id == 0) getActivity().onBackPressed();
    }
}
