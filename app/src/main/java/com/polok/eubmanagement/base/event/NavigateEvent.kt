package com.polok.eubmanagement.base.event

import android.os.Bundle

data class NavigateEvent(
    var id: Int = -1,
    var bundle: Bundle? = null
)
