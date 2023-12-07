package com.polok.eubmanagement.presentation.dashboard;

import android.os.Bundle;

import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentChatBinding;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class ChatFragment extends BaseFragment<FragmentChatBinding> {
    @Override
    protected FragmentChatBinding initViewBinding() {
        return FragmentChatBinding.inflate(getLayoutInflater());
    }
    @Override
    protected BaseViewModel initViewModel() {return null;}
    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

    }
    @Override
    protected PrimaryLoader initPrimaryLoader() {return null;}
}