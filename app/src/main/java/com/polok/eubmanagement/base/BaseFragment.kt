package com.polok.eubmanagement.base

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.polok.eubmanagement.base.event.EventObserver
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.util.hideSoftKeyBoard
import com.polok.eubmanagement.util.makeGone
import com.polok.eubmanagement.util.makeVisible
import com.polok.eubmanagement.util.showToast
import com.polok.eubmanagement.widget.PrimaryLoader

abstract class BaseFragment<VB: ViewBinding>(
    viewBindingFactory: (LayoutInflater) -> VB
) : Fragment() {

    protected val binding: VB by lazy {
        viewBindingFactory(layoutInflater)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        SharedPref.init(context)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return binding.root.apply {
            setOnTouchListener { view, _ ->
                view.hideSoftKeyBoard()
                return@setOnTouchListener false
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initOnCreateView(savedInstanceState)

        initViewModel()?.messageEvent?.observe(viewLifecycleOwner, EventObserver {
            if (it.isNotEmpty()) context.showToast(it)
        })
        initViewModel()?.loadingEvent?.observe(viewLifecycleOwner, EventObserver {
            if (initPrimaryLoader() != null) {
                if (it) initPrimaryLoader().makeVisible() else initPrimaryLoader().makeGone()
            }
        })
        initViewModel()?.navigateEvent?.observe(viewLifecycleOwner, EventObserver {
            onNavigateEvent(it.id, it.bundle)
        })
    }

    protected abstract fun initViewModel(): BaseViewModel?

    protected abstract fun initOnCreateView(savedInstanceState: Bundle?)

    protected abstract fun initPrimaryLoader(): PrimaryLoader?

    protected open fun onNavigateEvent(id: Int, bundle: Bundle?) {}
}