package com.polok.eubmanagement.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewbinding.ViewBinding;

public abstract class BaseFragment<VB extends ViewBinding> extends Fragment {
    protected VB binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = initViewBinding();
        initOnCreateView(savedInstanceState);
        return binding.getRoot();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    protected abstract VB initViewBinding();

    protected abstract BaseViewModel setViewModel();

    protected abstract void initOnCreateView(Bundle savedInstanceState);
}
