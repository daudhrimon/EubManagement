package com.polok.eubmanagement.presentation.schedule.schedulelist;

import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.base.model.OnNavigate;
import com.polok.eubmanagement.databinding.FragmentScheduleListBinding;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class ScheduleListFragment extends BaseFragment<FragmentScheduleListBinding> {
    @Override
    protected FragmentScheduleListBinding initViewBinding() {
        return FragmentScheduleListBinding.inflate(getLayoutInflater());
    }
    private ScheduleListViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this).get(ScheduleListViewModel.class);
    }
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

        viewModel.fetchScheduleListFromFirebase();

        viewModel.getScheduleLiveData().observe(getViewLifecycleOwner(), scheduleList-> {
            if (scheduleList != null && !scheduleList.isEmpty()) {
                binding.noticeRecycler.setAdapter(new ScheduleListAdapter(scheduleList));
            }
        });
        binding.addScheduleButton.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_scheduleListFragment_to_scheduleAddFragment);
        });
    }
    @Override
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}
}
