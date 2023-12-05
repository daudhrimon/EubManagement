package com.polok.eubmanagement.presentation.home.dashboard;

import android.os.Bundle;

import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentStudentBinding;

public class StudentFragment extends BaseFragment<FragmentStudentBinding> {
    @Override
    protected FragmentStudentBinding initViewBinding() {
        return FragmentStudentBinding.inflate(getLayoutInflater());
    }

    @Override
    protected BaseViewModel setViewModel() {
        return null;
    }

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

    }
}