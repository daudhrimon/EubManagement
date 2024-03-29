package com.polok.eubmanagement.presentation.dashboard.chat

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.polok.eubmanagement.R
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDbRef
import com.polok.eubmanagement.model.FacultyData
import com.polok.eubmanagement.presentation.faculty.facultylist.FacultyListViewModel
import com.polok.eubmanagement.presentation.faculty.facultyupdate.FacultyUpdateFragment

class ChatListViewModel  : BaseViewModel() {
    private val _facultyLiveData = MutableLiveData<List<FacultyData?>?>()
    val facultyLiveData: LiveData<List<FacultyData?>?> get() = _facultyLiveData

    fun fetchFacultyListFromFirebase() {
        fireLoadingEvent(true)
        FirebaseDbRef.provideFacultyRef()?.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val facultyList: MutableList<FacultyData?> = ArrayList()
                        for (moduleSnapshot in snapshot.getChildren()) {
                            if (moduleSnapshot.exists()) {
                                facultyList.add(
                                    moduleSnapshot.getValue(FacultyData::class.java)
                                )
                            }
                        }
                        try {
                            facultyList.reverse()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        } finally {
                            _facultyLiveData.postValue(facultyList)
                        }
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

    fun navigateToFacultyUpdateFragment(facultyData: FacultyData?) {
        if (facultyData != null) {
            fireNavigateEvent(
                R.id.action_facultyListFragment_to_facultyUpdateFragment,
                FacultyUpdateFragment.generateBundle(facultyData)
            )
        } else fireMessageEvent("Something went wrong")
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return FacultyListViewModel() as T
        }
    }
}