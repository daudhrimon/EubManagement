package com.polok.eubmanagement.presentation.schedule

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseActivity
import com.polok.eubmanagement.databinding.ActivityScheduleBinding

class ScheduleActivity : BaseActivity<ActivityScheduleBinding>(
    viewBindingFactory = ActivityScheduleBinding::inflate
) {
    override fun initOnCreate(savedInstanceState: Bundle?) {
        try {
            (supportFragmentManager.findFragmentById(
                R.id.fragment_container
            ) as NavHostFragment?)?.navController?.addOnDestinationChangedListener { _: NavController?, destination: NavDestination, _: Bundle? ->
                binding.toolBar.title.text = destination.label
            }
        } catch (_: Exception) { }

        binding.toolBar.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}