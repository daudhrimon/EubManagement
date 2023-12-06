package com.polok.eubmanagement.presentation.home.profile;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentProfileBinding;
import com.polok.eubmanagement.util.SharedPref;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {
    @Override
    protected FragmentProfileBinding initViewBinding() {
        return FragmentProfileBinding.inflate(getLayoutInflater());
    }
    ProfileViewModel viewModel;

    @Override
    protected BaseViewModel initViewModel() {
        return viewModel;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        SharedPref.init(context);
    }

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        viewModel = new ViewModelProvider(this).get(ProfileViewModel.class);

        viewModel.fetchUserProfileLiveData();

        viewModel.getUserProfileDataLiveData().observe(getViewLifecycleOwner(), userProfileData -> {
            if (userProfileData != null) {
                binding.userNameAndBatch.setText(
                        String.format("%s\n%s",
                                getText(userProfileData.getFullName()),
                                String.format("%s, Section %s",
                                        getText(userProfileData.getBatch()),
                                        getText(userProfileData.getSection())
                                )
                        )
                );
                binding.studentId.setText(getText(userProfileData.getStudentId()));
                binding.mobileNumber.setText(getText(userProfileData.getMobileNumber()));
                binding.email.setText(getText(userProfileData.getEmail()));
                binding.gender.setText(getText(userProfileData.getGender()));
                binding.blood.setText(getText(userProfileData.getBloodGroup()));
            }
        });
    }

    @Override
    protected PrimaryLoader initPrimaryLoader() {return null;}

    private String getText(String text) {
        if (text != null && !text.isEmpty()) return text;
        else return "N/A";
    }
}