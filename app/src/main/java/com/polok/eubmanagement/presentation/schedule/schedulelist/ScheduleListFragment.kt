package com.polok.eubmanagement.presentation.schedule.schedulelist

import android.os.Bundle
import androidx.fragment.app.viewModels
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
    private val viewModel: ScheduleListViewModel by viewModels {
        ScheduleListViewModel.Factory()
    }
    private val adapter: ScheduleListAdapter by lazy {
        ScheduleListAdapter(
            onUpdateClickListener = {

            }
        )
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchScheduleListFromFirebase()

        adapter.isAdmin = SharedPref.getUserProfile().isAdmin
        if (adapter.isAdmin == true) binding.addScheduleButton.makeVisible()

        viewModel.scheduleLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                binding.scheduleRecycler.adapter = adapter.apply {
                    submitList(it)
                }
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