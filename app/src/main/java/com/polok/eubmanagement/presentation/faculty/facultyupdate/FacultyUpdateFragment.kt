package com.polok.eubmanagement.presentation.faculty.facultyupdate

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentFacultyUpdateBinding
import com.polok.eubmanagement.model.FacultyData
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.widget.PrimaryLoader

class FacultyUpdateFragment : BaseFragment<FragmentFacultyUpdateBinding>(
    viewBindingFactory = FragmentFacultyUpdateBinding::inflate
) {
    private val viewModel: FacultyUpdateViewModel by viewModels {
        FacultyUpdateViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        binding.facultyName.setText(arguments?.getString("name"))
        binding.facultyDesignation.setText(arguments?.getString("details"))
        binding.facultyPhone.setText(arguments?.getString("phone"))

        resources.getStringArray(R.array.gender_spinner).apply {
            viewModel.genderZero = get(0)
            forEachIndexed { index, gender ->
                if (gender == arguments?.getString("gender")) {
                    binding.genderSpinner.setSelection(index)
                }
            }
        }

        binding.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?, view: View, pos: Int, l: Long
            ) {
                viewModel.gender = resources.getStringArray(R.array.gender_spinner)[pos]
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        binding.updateButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateFacultyInputsAndUploadToFirebase(
                key = arguments?.getString("key"),
                facultyNameEt = binding.facultyName,
                facultyDesignationEt = binding.facultyDesignation,
                facultyPhoneEt = binding.facultyPhone
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
            putString("details", facultyData?.details)
            putString("phone", facultyData?.phone)
            putString("gender", facultyData?.gender)
            putString("key", facultyData?.key)
        }
    }
}