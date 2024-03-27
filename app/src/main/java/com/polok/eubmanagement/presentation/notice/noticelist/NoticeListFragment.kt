package com.polok.eubmanagement.presentation.notice.noticelist

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentNoticeListBinding
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.util.makeVisible
import com.polok.eubmanagement.widget.PrimaryLoader

class NoticeListFragment : BaseFragment<FragmentNoticeListBinding>(
    viewBindingFactory = FragmentNoticeListBinding::inflate
) {
    private val viewModel: NoticeListViewModel by viewModels {
        NoticeListViewModel.Factory()
    }
    private val adapter: NoticeListAdapter by lazy {
        NoticeListAdapter { noticeData ->
            viewModel.navigateToViewNoticeFragment(noticeData)
        }
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchNoticeListFromFirebase()

        adapter.isAdmin = SharedPref.getUserProfile().isAdmin
        if (adapter.isAdmin == true) binding.addNoticeButton.makeVisible()

        viewModel.noticeLiveData.observe(viewLifecycleOwner) {
            if (it?.isNotEmpty() == true) {
                binding.noticeRecycler.adapter = adapter.apply {
                    submitList(it)
                }
            }
        }

        binding.addNoticeButton.setOnClickListener {
            viewModel.fireNavigateEvent(
                R.id.action_viewNoticeFragment_to_addNoticeFragment, null
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        findNavController().navigate(id, bundle)
    }
}