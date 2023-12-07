package com.polok.eubmanagement.presentation.notice.noticelist;

import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.base.model.OnNavigate;
import com.polok.eubmanagement.databinding.FragmentNoticeListBinding;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class NoticeListFragment extends BaseFragment<FragmentNoticeListBinding> {
    @Override
    protected FragmentNoticeListBinding initViewBinding() {
        return FragmentNoticeListBinding.inflate(getLayoutInflater());
    }
    private NoticeListViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this).get(NoticeListViewModel.class);
    }
    private NoticeListAdapter noticeListAdapter;
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

        viewModel.fetchNoticeListFromFirebase();

        viewModel.getNoticeLiveData().observe(getViewLifecycleOwner(), noticeList-> {
            if (noticeList != null && !noticeList.isEmpty()) {
                noticeListAdapter = new NoticeListAdapter(noticeList ,new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewModel.navigateToViewNoticeFragment(noticeList.get(noticeListAdapter.getAdapterPosition()));
                    }
                });
                binding.noticeRecycler.setAdapter(noticeListAdapter);
            }
        });
        binding.addNoticeButton.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_viewNoticeFragment_to_addNoticeFragment);
        });
    }
    @Override
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}
    @Override
    protected void onNavigateEvent(OnNavigate onNavigate) {
        super.onNavigateEvent(onNavigate);
        if (onNavigate != null && onNavigate.getBundle() != null) {
            Navigation.findNavController(binding.getRoot()).navigate(onNavigate.getId(),onNavigate.getBundle());
        }
    }
}
