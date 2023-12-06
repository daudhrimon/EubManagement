package com.polok.eubmanagement.presentation.auth;

import android.os.Bundle;
import com.polok.eubmanagement.base.BaseActivity;
import com.polok.eubmanagement.databinding.ActivityAuthBinding;
import com.polok.eubmanagement.util.Extension;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class AuthActivity extends BaseActivity<ActivityAuthBinding> {
    @Override
    protected ActivityAuthBinding initViewBinding() {
        return ActivityAuthBinding.inflate(getLayoutInflater());
    }

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        Extension.hideStatusBar(getWindow());
    }
}
