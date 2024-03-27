package com.polok.eubmanagement.presentation.notice.noticeview

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentNoticeViewBinding
import com.polok.eubmanagement.widget.PrimaryLoader

class NoticeViewFragment : BaseFragment<FragmentNoticeViewBinding>(
    viewBindingFactory = FragmentNoticeViewBinding::inflate
) {
    private val viewModel: NoticeViewViewModel by viewModels {
        NoticeViewViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchNoticeFromBundle(
            arguments?.getString("notice_data")
        )

        viewModel.noticeLiveData.observe(viewLifecycleOwner) {
            binding.createdAt.text = String.format("Created At: %s", it?.createdAt ?: "")
            binding.noticeTitle.setText(it?.title ?: "")
            binding.noticeDetails.setText(it?.details ?: "")
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader? = null
}