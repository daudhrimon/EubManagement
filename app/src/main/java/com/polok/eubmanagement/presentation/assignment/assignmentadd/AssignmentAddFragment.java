package com.polok.eubmanagement.presentation.assignment.assignmentadd;

import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentAssignmentAddBinding;
import com.polok.eubmanagement.util.SharedPref;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class AssignmentAddFragment extends BaseFragment<FragmentAssignmentAddBinding> {
    @Override
    protected FragmentAssignmentAddBinding initViewBinding() {
        return FragmentAssignmentAddBinding.inflate(getLayoutInflater());
    }
    AssignmentAddViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this).get(AssignmentAddViewModel.class);
    }
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

        binding.saveAssignmentButton.setOnClickListener(view -> {
            SharedPref.init(getContext());
            viewModel.validateAssignmentInputAndUploadToFirebase(
                    binding.assignmentTitle, binding.assignmentDetails
            );
        });
    }
    @Override
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}
    @Override
    protected void onNavigateEvent(int id, Bundle bundle) {
        super.onNavigateEvent(id, bundle);
        if (id == 0) getActivity().onBackPressed();
    }
}
