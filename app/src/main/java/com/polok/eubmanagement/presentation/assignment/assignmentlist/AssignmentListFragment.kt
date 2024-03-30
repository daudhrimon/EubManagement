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
        AssignmentListAdapter(
            onClickListener = {
                viewModel.navigateToAssignmentViewFragment(it)
            },
            onUpdateClickListener = {
                viewModel.navigateToAssignmentUpdateFragment(it)
            }
        )
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchAssignmentListFromFirebase()

        adapter.isAdmin = SharedPref.getUserProfile().admin
        if (adapter.isAdmin == true) binding.addAssignmentButton.makeVisible()

        viewModel.assignmentLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                binding.assignmentRecycler.adapter = adapter.apply {
                    submitList(it)
                }
            }
        }

        binding.addAssignmentButton.setOnClickListener {
            viewModel.fireNavigateEvent(
               R.id.action_assignmentListFragment_to_addAssignmentFragment, null
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        findNavController().navigate(id, bundle)
    }
}