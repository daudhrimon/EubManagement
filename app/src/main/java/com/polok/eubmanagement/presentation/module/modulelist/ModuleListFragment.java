package com.polok.eubmanagement.presentation.module.modulelist;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentModuleListBinding;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class ModuleListFragment extends BaseFragment<FragmentModuleListBinding> {
    @Override
    protected FragmentModuleListBinding initViewBinding() {
        return FragmentModuleListBinding.inflate(getLayoutInflater());
    }
    private ModuleListViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this).get(ModuleListViewModel.class);
    }
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

        viewModel.fetchModuleListFromFirebase();

        viewModel.getModuleLiveData().observe(getViewLifecycleOwner(), scheduleList-> {
            if (scheduleList != null && !scheduleList.isEmpty()) {
                binding.moduleRecycler.setAdapter(new ModuleListAdapter(scheduleList));
            }
        });
        binding.addModuleButton.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_moduleListFragment_to_moduleAddFragment);
        });
    }
    @Override
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}
}
