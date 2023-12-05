package com.polok.eubmanagement.presentation.home.dashboard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentAdminBinding;
import com.polok.eubmanagement.databinding.FragmentStudentBinding;

public class AdminFragment extends BaseFragment<FragmentAdminBinding> {
    @Override
    protected FragmentAdminBinding initViewBinding() {
        return FragmentAdminBinding.inflate(getLayoutInflater());
    }

    @Override
    protected BaseViewModel setViewModel() {
        return null;
    }

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

    }
}