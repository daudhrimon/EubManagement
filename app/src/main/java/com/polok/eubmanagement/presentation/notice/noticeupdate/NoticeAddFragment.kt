package com.polok.eubmanagement.presentation.notice.noticeupdate

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.polok.eubmanagement.base.BaseFragment
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.databinding.FragmentNoticeAddBinding
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.widget.PrimaryLoader

class NoticeAddFragment : BaseFragment<FragmentNoticeAddBinding>(
    viewBindingFactory = FragmentNoticeAddBinding::inflate
) {
    private val viewModel: NoticeAddViewModel by viewModels {
        NoticeAddViewModel.Factory()
    }

    override fun initViewModel(): BaseViewModel = viewModel

    override fun initOnCreateView(savedInstanceState: Bundle?) {

        binding.saveNoticeButton.setOnClickListener {
            SharedPref.init(context)
            viewModel.validateNoticeInputAndUploadToFirebase(
                binding.noticeTitle, binding.noticeDetails
            )
        }
    }

    override fun initPrimaryLoader(): PrimaryLoader = binding.primaryLoader

    override fun onNavigateEvent(id: Int, bundle: Bundle?) {
        super.onNavigateEvent(id, bundle)
        if (id == 0) activity?.onBackPressedDispatcher?.onBackPressed()
    }
}