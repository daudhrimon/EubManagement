package com.polok.eubmanagement.presentation.assignment.assignmentview

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentAssignmentViewBinding
import com.polok.eubmanagement.widget.PrimaryLoader

class AssignmentViewFragment : BaseFragment<FragmentAssignmentViewBinding>(
    viewBindingFactory = FragmentAssignmentViewBinding::inflate
) {
    private val viewModel: AssignmentViewViewModel by viewModels {
        AssignmentViewViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchAssignmentFromBundle(arguments?.getString("assignment_data"))

        viewModel.assignmentLiveData.observe(viewLifecycleOwner) {
            binding.createdAt.text = String.format("Created At: %s", it?.createdAt ?: "")
            binding.assignmentTitle.setText(it?.title ?: "")
            binding.assignmentDetails.setText(it?.details ?: "")
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader? = null
}