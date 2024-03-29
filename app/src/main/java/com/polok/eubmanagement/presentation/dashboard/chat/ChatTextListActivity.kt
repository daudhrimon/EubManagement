package com.polok.eubmanagement.presentation.dashboard.chat

import android.os.Bundle
import androidx.activity.viewModels
import com.polok.eubmanagement.base.BaseActivity
import com.polok.eubmanagement.base.event.EventObserver
import com.polok.eubmanagement.databinding.ActivityChatTextListBinding
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.util.makeGone
import com.polok.eubmanagement.util.makeVisible
import com.polok.eubmanagement.util.showToast

class ChatTextListActivity : BaseActivity<ActivityChatTextListBinding> (
    viewBindingFactory = ActivityChatTextListBinding::inflate
) {
    private val viewModel: ChatTextListViewModel by viewModels {
        ChatTextListViewModel.Factory(
            ownUserId = SharedPref.getUserProfile().userId
        )
    }

    override fun initOnCreate(savedInstanceState: Bundle?) {
        binding.toolBar.title.text = intent?.getStringExtra(CLIENT_NAME) ?: ""

        viewModel.loadingEvent?.observe(this, EventObserver {
            if (it) binding.primaryLoader.makeVisible()
            else binding.primaryLoader.makeGone()
        })

        viewModel.validateIdsAndFetchChatTextListFromFirebase(
            clientUserId = intent?.getStringExtra(CLIENT_USER_ID)
        )

        viewModel.chatTextLiveData.observe(this) {
            binding.chatRecycler.adapter = ChatTextListAdapter().apply {
                submitList(facultyList = it, ownUserId = viewModel.ownUserId)
            }
        }

        binding.sendButton.setOnClickListener {
            viewModel.validateIdsAndUploadToFirebase(
                textInputEt = binding.textInput,
                sendButton = binding.sendButton,
                clientUserId = intent?.getStringExtra(CLIENT_USER_ID)
            )
        }

        viewModel.messageEvent?.observe(this, EventObserver {
            if (it.isNotEmpty()) showToast(it)
        })

        binding.toolBar.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }

    companion object {
        const val CLIENT_NAME = "CLIENT_NAME"
        const val CLIENT_USER_ID = "CLIENT_USER_ID"
    }
}