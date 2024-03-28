package com.polok.eubmanagement.presentation.schedule.scheduleadd

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentScheduleAddBinding
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.widget.PrimaryLoader

class ScheduleAddFragment : BaseFragment<FragmentScheduleAddBinding>(
    viewBindingFactory = FragmentScheduleAddBinding::inflate
) {
    private val viewModel: ScheduleAddViewModel by viewModels {
        ScheduleAddViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.dayZero = resources.getStringArray(R.array.day_spinner)[0]

        binding.daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?, view: View, pos: Int, l: Long
            ) {
                viewModel.day = resources.getStringArray(R.array.day_spinner)[pos]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }

        binding.submitButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateScheduleInputAndUploadToFirebase(
                binding.courseTitle,
                binding.courseCode,
                binding.lecturerName,
                binding.startEndTime,
                binding.roomNo
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) activity?.onBackPressedDispatcher?.onBackPressed()
    }
}