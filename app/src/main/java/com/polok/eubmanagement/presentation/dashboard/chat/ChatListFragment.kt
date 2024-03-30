package com.polok.eubmanagement.presentation.dashboard.chat

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentChatListBinding
import com.polok.eubmanagement.presentation.classmatelist.ClassmateListActivity
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.widget.PrimaryLoader

class ChatListFragment : BaseFragment<FragmentChatListBinding>(
    viewBindingFactory = FragmentChatListBinding::inflate
) {
    private val viewModel: ChatListViewModel by viewModels {
        ChatListViewModel.Factory(
            ownUserId = SharedPref.getUserProfile().userId
        )
    }
    private val adapter: ChatListAdapter by lazy {
        ChatListAdapter(
            onClickListener = {
                startActivity(
                    Intent(requireContext(), ChatTextListActivity::class.java).apply {
                        putExtra(ChatTextListActivity.CLIENT_NAME, it?.name)
                        putExtra(ChatTextListActivity.CLIENT_USER_ID, it?.userId)
                    }
                )
            }
        )
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchChatListThatContainsMyId()

        viewModel.chatsLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) binding.chatRecycler.adapter = adapter.apply {
                submitList(it)
            }
        }

        binding.addChatButton.setOnClickListener {
            startActivity(
                Intent(requireContext(), ClassmateListActivity::class.java)
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader
}