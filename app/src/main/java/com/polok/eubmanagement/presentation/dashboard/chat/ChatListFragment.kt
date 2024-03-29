package com.polok.eubmanagement.presentation.dashboard.chat

import android.content.Intent
import android.os.Bundle
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentChatListBinding
import com.polok.eubmanagement.presentation.classmatelist.ClassmateListActivity
import com.polok.eubmanagement.widget.PrimaryLoader

class ChatListFragment : BaseFragment<FragmentChatListBinding>(
    viewBindingFactory = FragmentChatListBinding::inflate
) {
    override fun initViewModel(): BaseViewModel? = null

    override fun initOnCreateView(savedInstanceState: Bundle?) {
        binding.root.setOnClickListener {
            startActivity(Intent(requireContext(), ClassmateListActivity::class.java))
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader? = null
}