package com.polok.eubmanagement.presentation.dashboard;

import android.content.Intent;
import android.os.Bundle;
import androidx.lifecycle.ViewModelProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentProfileBinding;
import com.polok.eubmanagement.presentation.auth.AuthActivity;
import com.polok.eubmanagement.util.SharedPref;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {
    @Override
    protected FragmentProfileBinding initViewBinding() {
        return FragmentProfileBinding.inflate(getLayoutInflater());
    }
    HomeViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
    }
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

        viewModel.fetchUserProfileLiveData();

        viewModel.getUserProfileDataLiveData().observe(getViewLifecycleOwner(), userProfileData -> {
            if (userProfileData != null) {
                binding.userNameAndBatch.setText(
                        String.format("%s\n%s",
                                userProfileData.getNotNullText(userProfileData.getFullName()),
                                String.format("%s, Section %s",
                                        userProfileData.getNotNullText(SharedPref.getUserBatch()),
                                        userProfileData.getNotNullText(userProfileData.getSection())
                                )
                        )
                );
                binding.studentId.setText(userProfileData.getNotNullText(userProfileData.getStudentId()));
                binding.mobileNumber.setText(userProfileData.getNotNullText(userProfileData.getMobileNumber()));
                binding.email.setText(userProfileData.getNotNullText(userProfileData.getEmail()));
                binding.gender.setText(userProfileData.getNotNullText(userProfileData.getGender()));
                binding.blood.setText(userProfileData.getNotNullText(userProfileData.getBloodGroup()));
            }
        });
        binding.signOutButton.setOnClickListener(view -> {
            viewModel.attemptSignOutUser(FirebaseAuth.getInstance());
        });
    }
    @Override
    protected PrimaryLoader initPrimaryLoader() {return null;}
    @Override
    protected void onNavigateEvent(int id, Bundle bundle) {
        super.onNavigateEvent(id, bundle);
        if (id == 0) {
            startActivity(new Intent(getContext(), AuthActivity.class));
            getActivity().finish();
        }
    }
}