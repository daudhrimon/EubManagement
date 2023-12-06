package com.polok.eubmanagement.firebase;

import com.google.firebase.database.DatabaseReference;
import com.polok.eubmanagement.base.BaseApp;
import com.polok.eubmanagement.util.FirebaseChildTag;
import com.polok.eubmanagement.util.SharedPref;

public class FirebaseDataRef {
    public static DatabaseReference provideBatchRef() {
        return BaseApp.getFirebaseDataRef().child("BATCH_REF");
    }

    public static DatabaseReference provideStudentRef() {
        return BaseApp.getFirebaseDataRef().child("STUDENT").child(SharedPref.getUserBatch());
    }

    public static DatabaseReference provideClassModuleRef() {
        return BaseApp.getFirebaseDataRef().child("CLASS_MODULE").child(SharedPref.getUserBatch());
    }

    public static DatabaseReference provideNoticeRef() {
        return BaseApp.getFirebaseDataRef().child("NOTICE").child(SharedPref.getUserBatch());
    }

    public static DatabaseReference provideAssignmentRef() {
        return BaseApp.getFirebaseDataRef().child("ASSIGNMENT").child(SharedPref.getUserBatch());
    }

    public static DatabaseReference provideFacultyModuleRef() {
        return BaseApp.getFirebaseDataRef().child("FACULTY");
    }
}
