package com.polok.eubmanagement.presentation.auth

import android.os.Bundle
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseActivity
import com.polok.eubmanagement.databinding.ActivityAuthBinding
import com.polok.eubmanagement.util.getColorCompat

class AuthActivity : BaseActivity<ActivityAuthBinding>(
    viewBindingFactory = ActivityAuthBinding::inflate
) {
    override fun initOnCreate(savedInstanceState: Bundle?) {
        window.statusBarColor = resources.getColorCompat(R.color.color_window)
    }

    override fun onDestroy() {
        super.onDestroy()
        window.statusBarColor = resources.getColorCompat(R.color.color_primary)
    }
}