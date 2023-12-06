package com.polok.eubmanagement.presentation.notice.viewnotice;

import android.os.Bundle;
import android.util.Log;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.google.gson.Gson;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentViewNoticeBinding;
import com.polok.eubmanagement.util.SharedPref;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class ViewNoticeFragment extends BaseFragment<FragmentViewNoticeBinding> {
    @Override
    protected FragmentViewNoticeBinding initViewBinding() {
        return FragmentViewNoticeBinding.inflate(getLayoutInflater());
    }
    ViewNoticeViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this).get(ViewNoticeViewModel.class);
    }

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        SharedPref.init(getContext());
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

    @Override
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}
}
