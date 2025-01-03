package com.polok.eubmanagement.base.event

import androidx.lifecycle.Observer

class EventObserver<T>(private val onEventUnhandledContent: (T) -> Unit) : Observer<Event<T?>?> {

    override fun onChanged(value: Event<T?>?) {
        value?.getContentIfNotHandled()?.let { event ->
            onEventUnhandledContent(event)
        }
    }
}
