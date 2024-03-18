package com.polok.eubmanagement.presentation.auth

import android.os.Bundle
import com.polok.eubmanagement.base.BaseActivity
import com.polok.eubmanagement.databinding.ActivityAuthBinding
import com.polok.eubmanagement.util.hideStatusBar

class AuthActivity : BaseActivity<ActivityAuthBinding>(
    viewBindingFactory = ActivityAuthBinding::inflate
){
    override fun initOnCreate(savedInstanceState: Bundle?) {
        hideStatusBar()
    }
}