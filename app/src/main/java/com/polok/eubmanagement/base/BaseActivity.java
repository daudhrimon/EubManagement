package com.polok.eubmanagement.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {
    protected VB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = initViewBinding();
        setContentView(binding.getRoot());
        initOnCreateView(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }

    protected abstract VB initViewBinding();

    protected abstract BaseViewModel setViewModel();

    protected abstract void initOnCreateView(Bundle savedInstanceState);
}
