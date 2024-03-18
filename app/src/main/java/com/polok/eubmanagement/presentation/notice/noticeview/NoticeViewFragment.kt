package com.polok.eubmanagement.presentation.notice.noticeview

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentNoticeViewBinding
import com.polok.eubmanagement.widget.PrimaryLoader

class NoticeViewFragment : BaseFragment<FragmentNoticeViewBinding>(
    viewBindingFactory = FragmentNoticeViewBinding::inflate
) {
    private val viewModel: NoticeViewViewModel by lazy {
        ViewModelProvider(this)[NoticeViewViewModel::class.java]
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        viewModel.fetchNoticeFromBundle(
            arguments?.getString("notice_data")
        )

        viewModel.noticeLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.createdAt.text = String.format(
                    "Created At: %s", it.getNotNullText(it.createdAt)
                )
                binding.noticeTitle.setText(it.getNotNullText(it.title))
                binding.noticeDetails.setText(it.getNotNullText(it.details))
            }
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader? = null
}