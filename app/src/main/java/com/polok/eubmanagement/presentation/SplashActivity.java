package com.polok.eubmanagement.presentation;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.presentation.signin.SignInActivity;
import com.polok.eubmanagement.util.Extension;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Extension.hideStatusBar(getWindow());
        setContentView(R.layout.activity_splash);

        new Handler(Looper.myLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, SignInActivity.class));
                finish();
            }
        },2000);
    }
}