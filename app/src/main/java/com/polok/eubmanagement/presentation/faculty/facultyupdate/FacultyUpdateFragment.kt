package com.polok.eubmanagement.presentation.faculty.facultyupdate

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentModuleUpdateBinding
import com.polok.eubmanagement.model.FacultyData
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.widget.PrimaryLoader

class FacultyUpdateFragment : BaseFragment<FragmentModuleUpdateBinding>(
    viewBindingFactory = FragmentModuleUpdateBinding::inflate
) {
    private val viewModel: FacultyUpdateViewModel by viewModels {
        FacultyUpdateViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        binding.moduleTitle.setText(arguments?.getString("name"))
        binding.moduleLink.setText(arguments?.getString("designation"))

        binding.updateButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateFacultyInputsAndUploadToFirebase(
                key = arguments?.getString("key"),
                moduleTitleEt = binding.moduleTitle,
                moduleLinkEt = binding.moduleLink
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) activity?.onBackPressedDispatcher?.onBackPressed()
    }

    companion object {

        fun generateBundle(facultyData: FacultyData?) : Bundle = Bundle().apply {
            putString("name", facultyData?.name)
            putString("designation", facultyData?.designation)
            putString("phone", facultyData?.phone)
            putString("key", facultyData?.key)
        }
    }
}