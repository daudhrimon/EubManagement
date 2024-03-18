package com.polok.eubmanagement.presentation.dashboard

import android.os.Bundle
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.NavigationUI.setupWithNavController
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseActivity
import com.polok.eubmanagement.databinding.ActivityDashboardBinding

class DashboardActivity : BaseActivity<ActivityDashboardBinding>(
    viewBindingFactory = ActivityDashboardBinding::inflate
){
    override fun initOnCreate(savedInstanceState: Bundle?) {

        setupWithNavController(
            binding.bottomNav, findNavController(this, R.id.fragment_container)
        )
    }
}