package com.polok.eubmanagement.presentation.module.modulelist;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentModuleListBinding;
import com.polok.eubmanagement.util.Extension;
import com.polok.eubmanagement.util.SharedPref;
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
    private ModuleListAdapter moduleListAdapter;
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        if (SharedPref.getUserProfile().getAdmin()) binding.addModuleButton.setVisibility(View.VISIBLE);

        viewModel.fetchModuleListFromFirebase();

        viewModel.getModuleLiveData().observe(getViewLifecycleOwner(), scheduleList-> {
            if (scheduleList != null && !scheduleList.isEmpty()) {
                moduleListAdapter = new ModuleListAdapter(scheduleList, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            binding.getRoot().getContext().startActivity(
                                    new Intent(Intent.ACTION_VIEW, Uri.parse(moduleListAdapter.getModuleLink()))
                            );
                        } catch (Exception e) {
                            Extension.showToast(binding.getRoot().getContext(),e.getLocalizedMessage());
                        }
                    }
                });
                binding.moduleRecycler.setAdapter(moduleListAdapter);
            }
        });
        binding.addModuleButton.setOnClickListener(view -> {
            viewModel.fireNavigateEvent(R.id.action_moduleListFragment_to_moduleAddFragment,null);
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
