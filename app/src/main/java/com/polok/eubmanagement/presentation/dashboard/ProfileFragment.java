package com.polok.eubmanagement.presentation.dashboard;

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
    HomeViewModel viewModel;

    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(getActivity()).get(HomeViewModel.class);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        SharedPref.init(context);
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
    }

    @Override
    protected PrimaryLoader initPrimaryLoader() {return null;}
}