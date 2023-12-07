package com.polok.eubmanagement.presentation.assignment.assignmentlist;

import android.os.Bundle;
import android.view.View;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.base.model.OnNavigate;
import com.polok.eubmanagement.databinding.FragmentAssignmentListBinding;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class AssignmentListFragment extends BaseFragment<FragmentAssignmentListBinding> {
    @Override
    protected FragmentAssignmentListBinding initViewBinding() {
        return FragmentAssignmentListBinding.inflate(getLayoutInflater());
    }
    private AssignmentListViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this).get(AssignmentListViewModel.class);
    }
    private AssignmentListAdapter assignmentListAdapter;
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

        viewModel.fetchAssignmentListFromFirebase();

        viewModel.getAssignmentLiveData().observe(getViewLifecycleOwner(), assignmentList-> {
            if (assignmentList != null && !assignmentList.isEmpty()) {
                assignmentListAdapter = new AssignmentListAdapter(assignmentList ,new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewModel.navigateToViewAssignmentFragment(assignmentList.get(assignmentListAdapter.getAdapterPosition()));
                    }
                });
                binding.assignmentRecycler.setAdapter(assignmentListAdapter);
            }
        });
        binding.addAssignmentButton.setOnClickListener(view -> {
            viewModel.fireNavigateEvent(R.id.action_viewAssignmentFragment_to_addAssignmentFragment,null);
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
