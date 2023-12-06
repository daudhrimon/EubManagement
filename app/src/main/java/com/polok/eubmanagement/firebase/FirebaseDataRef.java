package com.polok.eubmanagement.firebase;

import com.google.firebase.database.DatabaseReference;
import com.polok.eubmanagement.base.BaseApp;
import com.polok.eubmanagement.util.FirebaseChildTag;

public class FirebaseDataRef {
    public static DatabaseReference provideStudentRef() {
        return BaseApp.getFirebaseDataRef().child("STUDENT");
    }

    public static DatabaseReference provideClassModuleRef() {
        return BaseApp.getFirebaseDataRef().child("CLASS_MODULE");
    }

    public static DatabaseReference provideNoticeRef() {
        return BaseApp.getFirebaseDataRef().child("NOTICE");
    }

    public static DatabaseReference provideAssignmentRef() {
        return BaseApp.getFirebaseDataRef().child("ASSIGNMENT");
    }

    public static DatabaseReference provideFacultyModuleRef() {
        return BaseApp.getFirebaseDataRef().child("FACULTY");
    }
}
