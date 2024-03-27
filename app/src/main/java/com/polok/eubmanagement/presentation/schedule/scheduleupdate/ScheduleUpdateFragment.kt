package com.polok.eubmanagement.presentation.schedule.scheduleupdate

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.fragment.app.viewModels
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentScheduleAddBinding
import com.polok.eubmanagement.model.ScheduleData
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.widget.PrimaryLoader

class ScheduleUpdateFragment : BaseFragment<FragmentScheduleAddBinding>(
    viewBindingFactory = FragmentScheduleAddBinding::inflate
) {
    private val viewModel: ScheduleUpdateViewModel by viewModels {
        ScheduleUpdateViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {
        val dayList = resources.getStringArray(R.array.day_spinner)
        viewModel.dayZero = dayList[0]

        binding.courseTitle.setText(arguments?.getString("course_title"))
        binding.courseCode.setText(arguments?.getString("course_code"))
        binding.lecturerName.setText(arguments?.getString("lecturer_name"))
        binding.startEndTime.setText(arguments?.getString("start_end_time"))
        binding.roomNo.setText(arguments?.getString("room_no"))

        binding.daySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                adapterView: AdapterView<*>?, view: View, pos: Int, l: Long
            ) {
                viewModel.day = dayList[pos]
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        dayList.forEachIndexed { index, day ->
            if (day == (arguments?.getString("day","") ?: "")) {
                binding.daySpinner.setSelection(index)
            }
        }

        binding.addScheduleButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateScheduleInputAndUploadToFirebase(
                key = arguments?.getString("key"),
                courseTitleEt = binding.courseTitle,
                courseCodeEt = binding.courseCode,
                lecturerNameEt = binding.lecturerName,
                startEndTimeEt = binding.startEndTime,
                roomNoEt = binding.roomNo
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) activity?.onBackPressedDispatcher?.onBackPressed()
    }

    companion object {

        fun processBundle(scheduleData: ScheduleData?) : Bundle = Bundle().apply {
            putString("course_title", scheduleData?.courseTitle)
            putString("course_code", scheduleData?.courseCode)
            putString("lecturer_name", scheduleData?.lecturerName)
            putString("start_end_time", scheduleData?.startEndTime)
            putString("room_no", scheduleData?.roomNo)
            putString("day", scheduleData?.day)
            putString("key", scheduleData?.key)
        }
    }
}