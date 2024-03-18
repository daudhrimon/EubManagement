package com.polok.eubmanagement.presentation.dashboard

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.auth.FirebaseAuth
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentProfileBinding
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.widget.PrimaryLoader

class ProfileFragment : BaseFragment<FragmentProfileBinding>(
    viewBindingFactory = FragmentProfileBinding::inflate
) {
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchUserProfileLiveData()

        viewModel.userProfileDataLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.userNameAndBatch.text = String.format(
                    "%s\n%s",
                    it.getNotNullText(it.fullName),
                    String.format(
                        "%s, Section %s",
                        it.getNotNullText(SharedPref.getUserBatch()),
                        it.getNotNullText(it.section)
                    )
                )
                binding.studentId.text = it.getNotNullText(it.studentId)
                binding.mobileNumber.text = it.getNotNullText(it.mobileNumber)
                binding.email.text = it.getNotNullText(it.email)
                binding.gender.text = it.getNotNullText(it.gender)
                binding.blood.text = it.getNotNullText(it.bloodGroup)
            }
        }
        binding.signOutButton.setOnClickListener {
            viewModel.attemptSignOutUser(FirebaseAuth.getInstance())
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader? = null
}