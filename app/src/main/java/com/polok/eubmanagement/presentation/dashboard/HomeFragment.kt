package com.polok.eubmanagement.presentation.dashboard

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentHomeBinding
import com.polok.eubmanagement.presentation.assignment.AssignmentActivity
import com.polok.eubmanagement.presentation.module.ModuleActivity
import com.polok.eubmanagement.presentation.notice.NoticeActivity
import com.polok.eubmanagement.presentation.notice.noticelist.NoticeListAdapter
import com.polok.eubmanagement.presentation.schedule.ScheduleActivity
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.util.makeVisible
import com.polok.eubmanagement.widget.PrimaryLoader

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    viewBindingFactory = FragmentHomeBinding::inflate
) {
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchUserProfileLiveData()
        viewModel.fetchNoticeListFromFirebase()

        viewModel.userProfileDataLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.userNameAndBatch.text = String.format("%s\n%s", it.fullName ?: "", String.format(
                        "%s, Section %s",
                        SharedPref.getUserBatch(),
                        it.section ?: ""
                    )
                )
            }
        }

        viewModel.noticeLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                binding.recentNoticeLayer.makeVisible()
                binding.recentNoticeRecycler.adapter = NoticeListAdapter(null).apply {
                    submitList(it)
                }

                binding.viewAllButton.setOnClickListener {
                    openAnotherActivity(NoticeActivity::class.java)
                }
            }
        }
        binding.classScheduleButton.setOnClickListener {
            openAnotherActivity(ScheduleActivity::class.java)
        }
        binding.noticeButton.setOnClickListener {
            openAnotherActivity(NoticeActivity::class.java)
        }
        binding.assignmentButton.setOnClickListener {
            openAnotherActivity(AssignmentActivity::class.java)
        }
        binding.courseModuleButton.setOnClickListener {
            openAnotherActivity(ModuleActivity::class.java)
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    private fun openAnotherActivity(destinationClass: Class<*>) {
        startActivity(Intent(context, destinationClass))
    }
}