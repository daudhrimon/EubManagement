package com.polok.eubmanagement.presentation.assignment.assignmentadd

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentAssignmentAddBinding
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.widget.PrimaryLoader

class AssignmentAddFragment : BaseFragment<FragmentAssignmentAddBinding>(
    viewBindingFactory = FragmentAssignmentAddBinding::inflate
) {
    private val viewModel: AssignmentAddViewModel by viewModels {
        AssignmentAddViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        binding.saveButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateAssignmentInputAndUploadToFirebase(
                binding.assignmentTitle, binding.assignmentDetails
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) activity?.onBackPressedDispatcher?.onBackPressed()
    }
}