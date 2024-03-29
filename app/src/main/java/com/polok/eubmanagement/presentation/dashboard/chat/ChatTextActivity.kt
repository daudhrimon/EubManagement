package com.polok.eubmanagement.presentation.dashboard.chat

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import com.google.gson.Gson
import com.polok.eubmanagement.base.BaseActivity
import com.polok.eubmanagement.base.event.EventObserver
import com.polok.eubmanagement.databinding.ActivityChatTextBinding
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.util.makeGone
import com.polok.eubmanagement.util.makeVisible
import com.polok.eubmanagement.util.showToast

class ChatTextActivity : BaseActivity<ActivityChatTextBinding> (
    viewBindingFactory = ActivityChatTextBinding::inflate
) {
    private val viewModel: ChatTextViewModel by viewModels {
        ChatTextViewModel.Factory(
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
            Log.wtf("DATA", Gson().toJson(it))
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