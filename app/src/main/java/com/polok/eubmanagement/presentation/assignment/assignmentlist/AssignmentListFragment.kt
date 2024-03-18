package com.polok.eubmanagement.presentation.assignment.assignmentlist

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
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
    private val viewModel: AssignmentListViewModel by lazy {
        ViewModelProvider(this)[AssignmentListViewModel::class.java]
    }

    override fun initViewModel(): BaseViewModel = viewModel

    private var assignmentListAdapter: AssignmentListAdapter? = null

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        if (SharedPref.getUserProfile().isAdmin == true) binding.addAssignmentButton.makeVisible()

        viewModel.fetchAssignmentListFromFirebase()

        viewModel.assignmentLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                assignmentListAdapter = AssignmentListAdapter(it) { _ ->
                    viewModel.navigateToViewAssignmentFragment(
                        it[assignmentListAdapter?.adapterPosition ?: 0]
                    )
                }
                binding.assignmentRecycler.setAdapter(assignmentListAdapter)
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