package com.polok.eubmanagement.presentation.auth.signin

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentSigninBinding
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.widget.PrimaryLoader

class SignInFragment : BaseFragment<FragmentSigninBinding>(
    viewBindingFactory = FragmentSigninBinding::inflate
){
    private val viewModel: SignInViewModel by lazy {
        ViewModelProvider(this)[SignInViewModel::class.java]
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        binding.signInButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateInputItemsAndExecuteSignup(
                binding.emailInput, binding.passwordInput,
                FirebaseAuth.getInstance()
            )
        }
        binding.signUpButton.setOnClickListener {
            findNavController().navigate(
                R.id.action_signInFragment_to_signupFragment
            )
        }

        binding.emailInput.setText("daud@daud.com")
        binding.passwordInput.setText("123456")
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader
}