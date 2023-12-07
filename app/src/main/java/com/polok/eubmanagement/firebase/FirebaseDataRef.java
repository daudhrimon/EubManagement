package com.polok.eubmanagement.firebase;

import com.google.firebase.database.DatabaseReference;
import com.polok.eubmanagement.base.BaseApp;
import com.polok.eubmanagement.util.SharedPref;

public class FirebaseDataRef {
    public static DatabaseReference provideBatchRef() {
        return BaseApp.getFirebaseDataRef().child("BATCH_REF");
    }

    public static DatabaseReference provideStudentRef() {
        return BaseApp.getFirebaseDataRef().child("STUDENT").child(SharedPref.getUserBatch());
    }

    public static DatabaseReference provideScheduleRef() {
        return BaseApp.getFirebaseDataRef().child("SCHEDULE").child(SharedPref.getUserBatch());
    }

    public static DatabaseReference provideNoticeRef() {
        return BaseApp.getFirebaseDataRef().child("NOTICE").child(SharedPref.getUserBatch());
    }

    public static DatabaseReference provideAssignmentRef() {
        return BaseApp.getFirebaseDataRef().child("ASSIGNMENT").child(SharedPref.getUserBatch());
    }

    public static DatabaseReference provideModuleRef() {
        return BaseApp.getFirebaseDataRef().child("MODULE");
    }

    public static DatabaseReference provideFacultyRef() {
        return BaseApp.getFirebaseDataRef().child("FACULTY");
    }
}
