package com.polok.eubmanagement.presentation.schedule.schedulelist;

import android.os.Bundle;
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
                binding.scheduleRecycler.setAdapter(new ScheduleListAdapter(scheduleList));
            }
        });
        binding.addScheduleButton.setOnClickListener(view -> {
            viewModel.fireNavigateEvent(new OnNavigate(R.id.action_scheduleListFragment_to_scheduleAddFragment));
        });
    }
    @Override
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}

    @Override
    protected void onNavigateEvent(OnNavigate onNavigate) {
        super.onNavigateEvent(onNavigate);
        if (onNavigate != null) {
            Navigation.findNavController(binding.getRoot()).navigate(onNavigate.getId(),onNavigate.getBundle());
        }
    }
}
