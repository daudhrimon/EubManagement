package com.polok.eubmanagement.base;

import android.app.Application;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.polok.eubmanagement.util.SharedPref;

public class BaseApp extends Application {
    private static DatabaseReference databaseReference;

    @Override
    public void onCreate() {
        super.onCreate();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        SharedPref.init(getApplicationContext());
    }

    public static DatabaseReference getFirebaseDataRef() {
        return databaseReference;
    }
}
