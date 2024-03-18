package com.polok.eubmanagement.presentation

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.polok.eubmanagement.R
import com.polok.eubmanagement.presentation.auth.AuthActivity
import com.polok.eubmanagement.util.hideStatusBar

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        hideStatusBar()
        setContentView(R.layout.activity_splash)

        window?.decorView?.postDelayed({
            startActivity(Intent(this, AuthActivity::class.java))
            finish()
        }, 200)
    }
}