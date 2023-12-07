package com.polok.eubmanagement.presentation.auth.signin;

import android.content.Intent;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.google.firebase.auth.FirebaseAuth;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentSigninBinding;
import com.polok.eubmanagement.presentation.dashboard.DashboardActivity;
import com.polok.eubmanagement.util.SharedPref;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class SignInFragment extends BaseFragment<FragmentSigninBinding> {
    @Override
    protected FragmentSigninBinding initViewBinding() {
        return FragmentSigninBinding.inflate(getLayoutInflater());
    }
    private SignInViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
    }
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        binding.loginButton.setOnClickListener(view -> {
            SharedPref.init(getContext());
            viewModel.validateInputItemsAndExecuteSignup(
                    binding.emailInput,binding.passwordInput,
                    FirebaseAuth.getInstance()
            );
        });
        binding.forgotPassword.setOnClickListener(view -> {

        });
        binding.signupButton.setOnClickListener(view -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_signInFragment_to_signupFragment);
        });
        binding.emailInput.setText("daud@daud.com");
        binding.passwordInput.setText("123456");
    }
    @Override
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}
    @Override
    protected void onNavigateEvent(int id, Bundle bundle) {
        super.onNavigateEvent(id, bundle);
        if (id == 1) {
            startActivity(new Intent(getContext(), DashboardActivity.class));
            getActivity().finish();
        }
    }
}