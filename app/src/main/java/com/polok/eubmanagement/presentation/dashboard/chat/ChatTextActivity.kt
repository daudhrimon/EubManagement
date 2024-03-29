package com.polok.eubmanagement.presentation.dashboard.chat

import android.os.Bundle
import androidx.activity.viewModels
import com.polok.eubmanagement.base.BaseActivity
import com.polok.eubmanagement.databinding.ActivityChatTextBinding

class ChatTextActivity : BaseActivity<ActivityChatTextBinding> (
    viewBindingFactory = ActivityChatTextBinding::inflate
) {
    private val viewModel: ChatTextViewModel by viewModels {
        ChatTextViewModel.Factory()
    }

    override fun initOnCreate(savedInstanceState: Bundle?) {
        binding.toolBar.title.text = intent?.getStringExtra("name") ?: ""

        binding.sendButton.setOnClickListener {

        }
        binding.toolBar.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}