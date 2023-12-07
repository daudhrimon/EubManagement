package com.polok.eubmanagement.presentation.schedule.schedulelist;

import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentScheduleListBinding;
import com.polok.eubmanagement.util.SharedPref;
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
        if (SharedPref.getUserProfile().getAdmin()) binding.addScheduleButton.setVisibility(View.VISIBLE);

        viewModel.fetchScheduleListFromFirebase();

        viewModel.getScheduleLiveData().observe(getViewLifecycleOwner(), scheduleList-> {
            if (scheduleList != null && !scheduleList.isEmpty()) {
                binding.scheduleRecycler.setAdapter(new ScheduleListAdapter(scheduleList));
            }
        });
        binding.addScheduleButton.setOnClickListener(view -> {
            viewModel.fireNavigateEvent(R.id.action_scheduleListFragment_to_scheduleAddFragment, null);
        });
    }
    @Override
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}
    @Override
    protected void onNavigateEvent(int id, Bundle bundle) {
        super.onNavigateEvent(id, bundle);
        Navigation.findNavController(binding.getRoot()).navigate(id, bundle);
    }
}
