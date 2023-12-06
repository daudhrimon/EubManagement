package com.polok.eubmanagement.base;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.polok.eubmanagement.widget.PrimaryLoader;

public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {
    protected VB binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = initViewBinding();
        setContentView(binding.getRoot());
        initOnCreateView(savedInstanceState);
    }

    protected abstract VB initViewBinding();

    protected abstract void initOnCreateView(Bundle savedInstanceState);

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
