package com.polok.eubmanagement.presentation.module.modulelist

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
import com.polok.eubmanagement.model.ModuleData
import com.polok.eubmanagement.presentation.module.moduleupdate.ModuleUpdateFragment

class ModuleListViewModel : BaseViewModel() {
    private val _moduleLiveData = MutableLiveData<List<ModuleData?>?>()
    val moduleLiveData: LiveData<List<ModuleData?>?> get() = _moduleLiveData

    fun fetchModuleListFromFirebase() {
        fireLoadingEvent(true)
        FirebaseDbRef.provideModuleRef()?.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val moduleList: MutableList<ModuleData?> = ArrayList()
                        for (moduleSnapshot in snapshot.getChildren()) {
                            if (moduleSnapshot.exists()) moduleList.add(
                                moduleSnapshot.getValue(
                                    ModuleData::class.java
                                )
                            )
                        }
                        try {
                            moduleList.reverse()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        } finally {
                            _moduleLiveData.postValue(moduleList)
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

    fun navigateToModuleUpdateFragment(moduleData: ModuleData?) {
        if (moduleData != null) {
            fireNavigateEvent(
                R.id.action_moduleListFragment_to_moduleUpdateFragment,
                ModuleUpdateFragment.generateBundle(moduleData)
            )
        } else fireMessageEvent("Something went wrong")
    }

    class Factory : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ModuleListViewModel() as T
        }
    }
}