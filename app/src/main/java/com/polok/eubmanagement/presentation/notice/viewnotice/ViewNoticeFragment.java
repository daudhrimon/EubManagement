package com.polok.eubmanagement.presentation.notice.viewnotice;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.gson.Gson;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.base.event.Event;
import com.polok.eubmanagement.databinding.FragmentViewNoticeBinding;

public class ViewNoticeFragment extends BaseFragment<FragmentViewNoticeBinding> {
    @Override
    protected FragmentViewNoticeBinding initViewBinding() {
        return FragmentViewNoticeBinding.inflate(getLayoutInflater());
    }
    ViewNoticeViewModel viewModel;

    @Override
    protected BaseViewModel setViewModel() {return viewModel;}

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ViewNoticeViewModel.class);

        viewModel.getLoadingEvent().observe(getViewLifecycleOwner(), new Event.EventObserver<>(isLoading -> {
            if (isLoading) binding.primaryLoader.setVisibility(View.VISIBLE);
            else binding.primaryLoader.setVisibility(View.GONE);
        }));

        viewModel.fetchNoticeListFromFirebase();

        viewModel.getNoticeLiveData().observe(getViewLifecycleOwner(), noticeList-> {
            if (noticeList != null && !noticeList.isEmpty()) {
                Log.wtf("NoticeList", new Gson().toJson(noticeList));
            }
        });

        binding.addNoticeButton.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_viewNoticeFragment_to_addNoticeFragment);
        });
    }
}
