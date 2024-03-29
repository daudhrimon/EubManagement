package com.polok.eubmanagement.presentation.classmatelist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.polok.eubmanagement.base.BaseActivity
import com.polok.eubmanagement.base.event.EventObserver
import com.polok.eubmanagement.databinding.ActivityClassmateListBinding
import com.polok.eubmanagement.util.makeGone
import com.polok.eubmanagement.util.makeVisible
import com.polok.eubmanagement.util.showToast

class ClassmateListActivity : BaseActivity<ActivityClassmateListBinding>(
    viewBindingFactory = ActivityClassmateListBinding::inflate
) {
    private val viewModel: ClassMateListViewModel by viewModels {
        ClassMateListViewModel.Factory(
            ownUserId = FirebaseAuth.getInstance().currentUser?.uid
        )
    }
    private val adapter: ClassMateListAdapter by lazy {
        ClassMateListAdapter(
            onCallNowClickListener = {
                try {
                    startActivity(
                        Intent().apply {
                            action = Intent.ACTION_DIAL
                            data = Uri.parse("tel:$it")
                        }
                    )
                } catch (e: Exception) {
                    showToast("something went wrong")
                    e.printStackTrace()
                }
            }
        )
    }

    override fun initOnCreate(savedInstanceState: Bundle?) {
        viewModel.loadingEvent?.observe(this, EventObserver {
            if (it) binding.primaryLoader.makeVisible()
            else binding.primaryLoader.makeGone()
        })

        viewModel.fetchClassMateListFromFirebase()

        viewModel.classMateLiveData.observe(this) {
            if (it?.isNotEmpty() == true) {
                binding.classMateRecycler.adapter = adapter.apply {
                    submitList(it)
                }
            }
        }

        viewModel.messageEvent?.observe(this, EventObserver {
            if (it.isNotEmpty()) showToast(it)
        })

        binding.toolBar.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}