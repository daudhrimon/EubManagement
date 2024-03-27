package com.polok.eubmanagement.presentation.notice.noticeupdate

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentNoticeAddBinding
import com.polok.eubmanagement.model.NoticeData
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.widget.PrimaryLoader

class NoticeUpdateFragment : BaseFragment<FragmentNoticeAddBinding>(
    viewBindingFactory = FragmentNoticeAddBinding::inflate
) {
    private val viewModel: NoticeUpdateViewModel by viewModels {
        NoticeUpdateViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        binding.noticeTitle.setText(arguments?.getString("title"))
        binding.noticeDetails.setText(arguments?.getString("details"))

        binding.saveNoticeButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateNoticeInputAndUploadToFirebase(
                key = arguments?.getString("key"),
                noticeTitleEt = binding.noticeTitle,
                noticeDetailsEt = binding.noticeDetails
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) activity?.onBackPressedDispatcher?.onBackPressed()
    }

    companion object {

        fun processBundle(noticeData: NoticeData?) : Bundle = Bundle().apply {
            putString("title", noticeData?.title)
            putString("details", noticeData?.details)
            putString("created_at", noticeData?.createdAt)
            putString("key", noticeData?.key)
        }
    }
}