package com.polok.eubmanagement.firebase

import com.google.firebase.database.DatabaseReference
import com.polok.eubmanagement.base.BaseApp
import com.polok.eubmanagement.util.SharedPref.getUserBatch

object FirebaseDbRef {

    fun provideBatchRef(): DatabaseReference? {
        return BaseApp.firebaseDataRef?.child("BATCH_REF")
    }

    fun provideStudentRef(): DatabaseReference? {
        return BaseApp.firebaseDataRef?.child("STUDENT")?.child(getUserBatch())
    }

    fun provideScheduleRef(): DatabaseReference? {
        return BaseApp.firebaseDataRef?.child("SCHEDULE")?.child(getUserBatch())
    }

    fun provideNoticeRef(): DatabaseReference? {
        return BaseApp.firebaseDataRef?.child("NOTICE")?.child(getUserBatch())
    }

    fun provideAssignmentRef(): DatabaseReference? {
        return BaseApp.firebaseDataRef?.child("ASSIGNMENT")?.child(getUserBatch())
    }

    fun provideModuleRef(): DatabaseReference? {
        return BaseApp.firebaseDataRef?.child("MODULE")?.child(getUserBatch())
    }

    fun provideFacultyRef(): DatabaseReference? {
        return BaseApp.firebaseDataRef?.child("FACULTY")?.child(getUserBatch())
    }

    fun provideChatRef(): DatabaseReference? {
        return BaseApp.firebaseDataRef?.child("CHAT")?.child(getUserBatch())
    }
}