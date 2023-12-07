package com.polok.eubmanagement.presentation.module.moduleadd;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.base.model.OnNavigate;
import com.polok.eubmanagement.databinding.FragmentModuleAddBinding;
import com.polok.eubmanagement.util.Extension;
import com.polok.eubmanagement.util.SharedPref;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class ModuleAddFragment extends BaseFragment<FragmentModuleAddBinding> {
    @Override
    protected FragmentModuleAddBinding initViewBinding() {
        return FragmentModuleAddBinding.inflate(getLayoutInflater());
    }
    ModuleAddViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this).get(ModuleAddViewModel.class);
    }
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

        binding.addModuleButton.setOnClickListener(view -> {
            SharedPref.init(getContext());
            viewModel.validateModuleInputsAndUploadToFirebase(
                    binding.moduleTitle, binding.moduleLink, Extension.getCurrentDate()
            );
        });
    }
    @Override
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}
    @Override
    protected void onNavigateEvent(OnNavigate onNavigate) {
        super.onNavigateEvent(onNavigate);
        if (onNavigate.getId() == 1) getActivity().onBackPressed();
    }
}
