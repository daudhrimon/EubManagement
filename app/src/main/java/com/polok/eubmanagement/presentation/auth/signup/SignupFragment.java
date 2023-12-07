package com.polok.eubmanagement.presentation.auth.signup;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import androidx.lifecycle.ViewModelProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentSignupBinding;
import com.polok.eubmanagement.util.SharedPref;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class SignupFragment extends BaseFragment<FragmentSignupBinding> {
    @Override
    protected FragmentSignupBinding initViewBinding() {
        return FragmentSignupBinding.inflate(getLayoutInflater());
    }
    private SignupViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this).get(SignupViewModel.class);
    }

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        binding.backButton.setOnClickListener(view -> {
            getActivity().onBackPressed();
        });

        viewModel.genderZero = getResources().getStringArray(R.array.gender_spinner)[0];
        viewModel.batchZero = getResources().getStringArray(R.array.batch_spinner)[0];
        viewModel.sectionZero = getResources().getStringArray(R.array.section_spinner)[0];
        viewModel.bloodGroupZero = getResources().getStringArray(R.array.blood_group_spinner)[0];

        binding.genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                viewModel.gender = getResources().getStringArray(R.array.gender_spinner)[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        binding.batchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                viewModel.batch = getResources().getStringArray(R.array.batch_spinner)[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        binding.sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                viewModel.section = getResources().getStringArray(R.array.section_spinner)[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        binding.bloodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                viewModel.bloodGroup = getResources().getStringArray(R.array.blood_group_spinner)[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        binding.signupButton.setOnClickListener(view -> {
            SharedPref.init(getContext());
            viewModel.validateInputItemsAndExecuteSignup(
                    binding.studentId, binding.fullName, binding.mobileInput, binding.emailInput, binding.passwordInput,
                    FirebaseAuth.getInstance()
            );
        });
    }

    @Override
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}
    @Override
    protected void onNavigateEvent(int id, Bundle bundle) {
        super.onNavigateEvent(id, bundle);
        if (id == 0) getActivity().onBackPressed();
    }
}