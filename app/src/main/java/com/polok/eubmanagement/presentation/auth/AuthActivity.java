package com.polok.eubmanagement.presentation.auth;

import android.os.Bundle;
import com.polok.eubmanagement.base.BaseActivity;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.databinding.ActivityAuthBinding;
import com.polok.eubmanagement.util.Extension;

public class AuthActivity extends BaseActivity<ActivityAuthBinding> {
    @Override
    protected ActivityAuthBinding initViewBinding() {
        return ActivityAuthBinding.inflate(getLayoutInflater());
    }
    @Override
    protected BaseViewModel setViewModel() {return null;}

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        Extension.hideStatusBar(getWindow());
    }
}
