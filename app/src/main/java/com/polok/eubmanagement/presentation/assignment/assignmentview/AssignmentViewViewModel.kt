package com.polok.eubmanagement.presentation.assignment.assignmentview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.model.AssignmentData

class AssignmentViewViewModel : BaseViewModel() {
    private val _assignmentLiveData = MutableLiveData<AssignmentData?>()
    val assignmentLiveData: LiveData<AssignmentData?> get() = _assignmentLiveData

    fun fetchAssignmentFromBundle(bundle: String?) {
        try {
            _assignmentLiveData.postValue(
                Gson().fromJson(bundle, AssignmentData::class.java)
            )
        } catch (e: Exception) {
            fireMessageEvent(e.localizedMessage)
        }
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AssignmentViewViewModel() as T
        }
    }
}