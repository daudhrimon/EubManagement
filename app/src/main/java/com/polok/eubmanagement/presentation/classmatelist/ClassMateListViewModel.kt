package com.polok.eubmanagement.presentation.classmatelist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDataRef.provideStudentRef
import com.polok.eubmanagement.model.UserProfileData

class ClassMateListViewModel : BaseViewModel() {
    private val _classMateLiveData = MutableLiveData<List<UserProfileData?>?>()
    val classMateLiveData: LiveData<List<UserProfileData?>?> get() = _classMateLiveData

    fun fetchClassMateListFromFirebase() {
        fireLoadingEvent(true)
        provideStudentRef()?.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val classMateList: MutableList<UserProfileData?> = ArrayList()
                        for (moduleSnapshot in snapshot.getChildren()) {
                            if (moduleSnapshot.exists()) {
                                classMateList.add(
                                    moduleSnapshot.getValue(UserProfileData::class.java)
                                )
                            }
                        }
                        try {
                            classMateList.reverse()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        } finally {
                            _classMateLiveData.postValue(classMateList)
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

    /*fun navigateToFacultyUpdateFragment(facultyData: FacultyData?) {
        if (facultyData != null) {
            fireNavigateEvent(
                R.id.action_facultyListFragment_to_facultyUpdateFragment,
                FacultyUpdateFragment.generateBundle(facultyData)
            )
        } else fireMessageEvent("Something went wrong")
    }*/

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ClassMateListViewModel() as T
        }
    }
}