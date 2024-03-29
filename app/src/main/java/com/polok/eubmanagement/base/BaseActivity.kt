package com.polok.eubmanagement.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.viewbinding.ViewBinding
import com.polok.eubmanagement.util.SharedPref

abstract class BaseActivity<VB: ViewBinding>(
    viewBindingFactory: (LayoutInflater) -> VB
) : AppCompatActivity() {

    protected val binding: VB by lazy {
        viewBindingFactory(layoutInflater)
    }
    protected var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        if (Build.VERSION.SDK_INT < 30) window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        else window?.setDecorFitsSystemWindows(true)*/
        setContentView(binding.root)
        SharedPref.init(this)
        initOnCreate(savedInstanceState)
    }

    abstract fun initOnCreate(savedInstanceState: Bundle?)
}