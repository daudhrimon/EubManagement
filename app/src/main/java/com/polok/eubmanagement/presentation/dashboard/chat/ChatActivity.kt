package com.polok.eubmanagement.presentation.dashboard.chat

import android.os.Bundle
import com.polok.eubmanagement.base.BaseActivity
import com.polok.eubmanagement.databinding.ActivityChatBinding

class ChatActivity : BaseActivity<ActivityChatBinding> (
    viewBindingFactory = ActivityChatBinding::inflate
) {
    override fun initOnCreate(savedInstanceState: Bundle?) {

        binding.toolBar.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}