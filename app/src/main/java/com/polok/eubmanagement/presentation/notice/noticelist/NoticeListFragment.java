package com.polok.eubmanagement.presentation.notice.noticelist;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentNoticeListBinding;
import com.polok.eubmanagement.util.SharedPref;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class NoticeListFragment extends BaseFragment<FragmentNoticeListBinding> {
    @Override
    protected FragmentNoticeListBinding initViewBinding() {
        return FragmentNoticeListBinding.inflate(getLayoutInflater());
    }
    NoticeListViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this).get(NoticeListViewModel.class);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        SharedPref.init(context);
    }

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

        viewModel.fetchNoticeListFromFirebase();

        viewModel.getNoticeLiveData().observe(getViewLifecycleOwner(), noticeList-> {
            if (noticeList != null && !noticeList.isEmpty()) {
                binding.noticeRecycler.setAdapter(new NoticeListAdapter(noticeList,true));
            }
        });
        binding.addNoticeButton.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_viewNoticeFragment_to_addNoticeFragment);
        });
    }

    @Override
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}
}
