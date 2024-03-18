package com.polok.eubmanagement.presentation.dashboard

import android.os.Bundle
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentChatBinding
import com.polok.eubmanagement.widget.PrimaryLoader

class ChatFragment : BaseFragment<FragmentChatBinding>(
    viewBindingFactory = FragmentChatBinding::inflate
) {
    override fun initViewModel(): BaseViewModel? = null

    override fun initOnCreateView(savedInstanceState: Bundle?) {

    }

    override fun initPrimaryLoader(): PrimaryLoader? = null
}