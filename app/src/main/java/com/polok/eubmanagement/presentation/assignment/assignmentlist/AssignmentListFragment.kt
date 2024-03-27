package com.polok.eubmanagement.presentation.assignment.assignmentlist

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentAssignmentListBinding
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.util.makeVisible
import com.polok.eubmanagement.widget.PrimaryLoader

class AssignmentListFragment : BaseFragment<FragmentAssignmentListBinding>(
    viewBindingFactory = FragmentAssignmentListBinding::inflate
) {
    private val viewModel: AssignmentListViewModel by viewModels {
        AssignmentListViewModel.Factory()
    }
    private val adapter: AssignmentListAdapter by lazy {
        AssignmentListAdapter {
            viewModel.navigateToViewAssignmentFragment(it)
        }
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        if (SharedPref.getUserProfile().isAdmin == true) binding.addAssignmentButton.makeVisible()

        viewModel.fetchAssignmentListFromFirebase()

        binding.assignmentRecycler.adapter = adapter

        viewModel.assignmentLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                binding.assignmentRecycler.adapter = AssignmentListAdapter { assignmentData ->
                    viewModel.navigateToViewAssignmentFragment(assignmentData)
                }.apply {
                    submitList(it)
                }
            }
        }

        binding.addAssignmentButton.setOnClickListener {
            viewModel.fireNavigateEvent(
               R.id.action_viewAssignmentFragment_to_addAssignmentFragment, null
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        findNavController().navigate(id, bundle)
    }
}