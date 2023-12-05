package com.polok.eubmanagement.presentation.home.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.FragmentChatBinding;

public class ChatFragment extends BaseFragment<FragmentChatBinding> {
    @Override
    protected FragmentChatBinding initViewBinding() {
        return FragmentChatBinding.inflate(getLayoutInflater());
    }

    @Override
    protected BaseViewModel setViewModel() {return null;}

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {

    }
}