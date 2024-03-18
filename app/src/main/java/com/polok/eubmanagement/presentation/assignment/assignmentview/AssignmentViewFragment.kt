package com.polok.eubmanagement.presentation.assignment.assignmentview

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentAssignmentViewBinding
import com.polok.eubmanagement.widget.PrimaryLoader

class AssignmentViewFragment : BaseFragment<FragmentAssignmentViewBinding>(
    viewBindingFactory = FragmentAssignmentViewBinding::inflate
) {
    private val viewModel: AssignmentViewViewModel by lazy {
        ViewModelProvider(this)[AssignmentViewViewModel::class.java]
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchAssignmentFromBundle(arguments?.getString("assignment_data"))

        viewModel.assignmentLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.createdAt.text = String.format("Created At: %s", it.getNotNullText(it.createdAt))
                binding.assignmentTitle.setText(it.getNotNullText(it.title))
                binding.assignmentDetails.setText(it.getNotNullText(it.details))
            }
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader? = null
}