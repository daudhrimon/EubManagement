package com.polok.eubmanagement.presentation.auth.signup

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentSignupBinding
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.widget.PrimaryLoader

class SignUpFragment : BaseFragment<FragmentSignupBinding>(
    viewBindingFactory = FragmentSignupBinding::inflate
){
    private val viewModel: SignUpViewModel by viewModels {
        SignUpViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        binding.backButton.setOnClickListener {
            activity?.onBackPressedDispatcher?.onBackPressed()
        }

        viewModel.genderZero = resources.getStringArray(R.array.gender_spinner)[0]
        viewModel.batchZero = resources.getStringArray(R.array.batch_spinner)[0]
        viewModel.sectionZero = resources.getStringArray(R.array.section_spinner)[0]
        viewModel.bloodGroupZero = resources.getStringArray(R.array.blood_group_spinner)[0]

        binding.genderSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?, view: View, pos: Int, l: Long
            ) {
                viewModel.gender = resources.getStringArray(R.array.gender_spinner)[pos]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        binding.batchSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?, view: View, pos: Int, l: Long
            ) {
                viewModel.batch = resources.getStringArray(R.array.batch_spinner)[pos]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        binding.sectionSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?, view: View, pos: Int, l: Long
                ) {
                    viewModel.section = resources.getStringArray(R.array.section_spinner)[pos]
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }
        binding.bloodGroupSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    adapterView: AdapterView<*>?, view: View, pos: Int, l: Long
                ) {
                    viewModel.bloodGroup = resources.getStringArray(R.array.blood_group_spinner)[pos]
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {}
            }

        binding.signUpButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateInputItemsAndExecuteSignup(
                binding.studentId,
                binding.fullName,
                binding.mobileInput,
                binding.emailInput,
                binding.passwordInput,
                FirebaseAuth.getInstance()
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) activity?.onBackPressedDispatcher?.onBackPressed()
    }
}