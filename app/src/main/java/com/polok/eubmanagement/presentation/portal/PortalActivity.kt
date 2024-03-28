package com.polok.eubmanagement.presentation.portal

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebViewClient
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseActivity
import com.polok.eubmanagement.databinding.ActivityPortalBinding

class PortalActivity : BaseActivity<ActivityPortalBinding>(
    viewBindingFactory = ActivityPortalBinding::inflate
) {
    @SuppressLint("SetJavaScriptEnabled")
    override fun initOnCreate(savedInstanceState: Bundle?) {

        binding.toolBar.title.text = getString(R.string.label_student_portal)

        binding.webView.webViewClient = WebViewClient()
        binding.webView.settings.javaScriptEnabled = true
        binding.webView.loadUrl("https://iems.eub.edu.bd/")

        binding.toolBar.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}