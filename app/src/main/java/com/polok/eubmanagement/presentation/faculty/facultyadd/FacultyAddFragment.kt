package com.polok.eubmanagement.presentation.faculty.facultyadd

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentFacultyAddBinding
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.util.getCurrentDate
import com.polok.eubmanagement.widget.PrimaryLoader

class FacultyAddFragment : BaseFragment<FragmentFacultyAddBinding>(
    viewBindingFactory = FragmentFacultyAddBinding::inflate
) {
    private val viewModel: FacultyAddViewModel by viewModels {
        FacultyAddViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        binding.saveButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateFacultyInputsAndUploadToFirebase(
                binding.moduleTitle, binding.moduleLink, getCurrentDate()
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) activity?.onBackPressedDispatcher?.onBackPressed()
    }
}