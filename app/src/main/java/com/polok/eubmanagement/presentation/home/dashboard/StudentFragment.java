package com.polok.eubmanagement.presentation.home.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

import com.polok.eubmanagement.R;
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