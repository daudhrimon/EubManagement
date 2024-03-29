package com.polok.eubmanagement.presentation.assignment.assignmentlist

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDbRef
import com.polok.eubmanagement.model.AssignmentData
import com.polok.eubmanagement.presentation.assignment.assignmentupdate.AssignmentUpdateFragment

class AssignmentListViewModel : BaseViewModel() {

    private val _assignmentLiveData = MutableLiveData<List<AssignmentData?>>()
    val assignmentLiveData: LiveData<List<AssignmentData?>> get() = _assignmentLiveData

    fun fetchAssignmentListFromFirebase() {
        fireLoadingEvent(true)
        FirebaseDbRef.provideAssignmentRef()?.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val assignmentList: MutableList<AssignmentData?> = ArrayList()
                        for (assignmentSnapshot in snapshot.getChildren()) {
                            if (assignmentSnapshot.exists()) assignmentList.add(
                                assignmentSnapshot.getValue(
                                    AssignmentData::class.java
                                )
                            )
                        }
                        _assignmentLiveData.postValue(assignmentList)
                        fireLoadingEvent(false)
                    } else fireLoadingEvent(false)
                }

                override fun onCancelled(error: DatabaseError) {
                    fireMessageEvent(error.message)
                    fireLoadingEvent(false)
                }
            }
        )
    }

    fun navigateToAssignmentViewFragment(assignmentData: AssignmentData?) {
        if (assignmentData != null) {
            fireNavigateEvent(
                R.id.action_assignmentListFragment_to_assignmentViewFragment,
                Bundle().apply { putString("assignment_data", Gson().toJson(assignmentData)) }
            )
        } else fireMessageEvent("Something went wrong")
    }

    fun navigateToAssignmentUpdateFragment(assignmentData: AssignmentData?) {
        if (assignmentData != null) {
            fireNavigateEvent(
                R.id.action_assignmentListFragment_to_assignmentUpdateFragment,
                AssignmentUpdateFragment.generateBundle(assignmentData)
            )
        } else fireMessageEvent("Something went wrong")
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AssignmentListViewModel() as T
        }
    }
}