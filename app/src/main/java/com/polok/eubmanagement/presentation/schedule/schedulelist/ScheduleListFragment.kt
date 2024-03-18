package com.polok.eubmanagement.presentation.schedule.schedulelist

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentScheduleListBinding
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.util.makeVisible
import com.polok.eubmanagement.widget.PrimaryLoader

class ScheduleListFragment : BaseFragment<FragmentScheduleListBinding>(
    viewBindingFactory = FragmentScheduleListBinding::inflate
) {
    private val viewModel: ScheduleListViewModel by lazy {
        ViewModelProvider(this)[ScheduleListViewModel::class.java]
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        if (SharedPref.getUserProfile().admin) binding.addScheduleButton.makeVisible()

        viewModel.fetchScheduleListFromFirebase()

        viewModel.scheduleLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                binding.scheduleRecycler.setAdapter(ScheduleListAdapter(it))
            }
        }

        binding.addScheduleButton.setOnClickListener {
            viewModel.fireNavigateEvent(
                R.id.action_scheduleListFragment_to_scheduleAddFragment, null
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        findNavController().navigate(id, bundle)
    }
}