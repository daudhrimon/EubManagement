package com.polok.eubmanagement.base

import android.app.Application
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.polok.eubmanagement.util.SharedPref

class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        firebaseDataRef = FirebaseDatabase.getInstance().getReference()
        SharedPref.init(applicationContext)
    }

    companion object {
        var firebaseDataRef: DatabaseReference? = null
            private set
    }
}
