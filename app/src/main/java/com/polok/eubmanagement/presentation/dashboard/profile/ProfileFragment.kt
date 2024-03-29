package com.polok.eubmanagement.presentation.dashboard.profile

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentProfileBinding
import com.polok.eubmanagement.presentation.dashboard.home.HomeViewModel
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.widget.PrimaryLoader

class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    viewBindingFactory = FragmentProfileBinding::inflate
) {
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchUserProfileLiveData()

        viewModel.userProfileDataLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.userNameAndBatch.text = String.format(
                    "%s\n%s", it.name ?: "",
                    String.format(
                        "%s, Section %s", SharedPref.getUserBatch(), it.section ?: ""
                    )
                )
                binding.studentId.text = it.studentId ?: ""
                binding.mobileNumber.text = it.phone ?: ""
                binding.email.text = it.email ?: ""
                binding.gender.text = it.gender ?: ""
                binding.blood.text = it.bloodGroup ?: ""
            }
        }
        binding.signOutButton.setOnClickListener {
            viewModel.attemptSignOutUser(FirebaseAuth.getInstance())
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader? = null
}