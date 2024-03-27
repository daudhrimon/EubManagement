package com.polok.eubmanagement.presentation.assignment.assignmentupdate

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentAssignmentAddBinding
import com.polok.eubmanagement.model.AssignmentData
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.widget.PrimaryLoader

class AssignmentUpdateFragment : BaseFragment<FragmentAssignmentAddBinding>(
    viewBindingFactory = FragmentAssignmentAddBinding::inflate
) {
    private val viewModel: AssignmentUpdateViewModel by viewModels {
        AssignmentUpdateViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        binding.assignmentTitle.setText(arguments?.getString("title"))
        binding.assignmentDetails.setText(arguments?.getString("details"))

        binding.saveAssignmentButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateAssignmentInputAndUploadToFirebase(
                key = arguments?.getString("key"),
                assignmentTitleEt = binding.assignmentTitle,
                assignmentDetailsEt = binding.assignmentDetails
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) activity?.onBackPressedDispatcher?.onBackPressed()
    }

    companion object {

        fun processBundle(assignmentData: AssignmentData?) : Bundle = Bundle().apply {
            putString("title", assignmentData?.title)
            putString("details", assignmentData?.details)
            putString("created_at", assignmentData?.createdAt)
            putString("key", assignmentData?.key)
        }
    }
}