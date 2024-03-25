package com.polok.eubmanagement.presentation.auth.signin

import android.util.Log
import android.util.Patterns
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDataRef
import com.polok.eubmanagement.model.UserProfileData
import com.polok.eubmanagement.util.SharedPref.saveUserBatch
import com.polok.eubmanagement.util.SharedPref.saveUserProfile
import com.polok.eubmanagement.util.showErrorOnUi

class SignInViewModel : BaseViewModel() {
    fun validateInputItemsAndExecuteSignup(
        emailInputEt: EditText, passwordInputEt: EditText, firebaseAuth: FirebaseAuth
    ) {
        if (!Patterns.EMAIL_ADDRESS.matcher(emailInputEt.getText().toString()).matches()) {
            emailInputEt.showErrorOnUi("Enter A Valid Email Address")
            return
        }
        if (passwordInputEt.getText().toString()
                .isEmpty() || passwordInputEt.getText().length < 6
        ) {
            passwordInputEt.showErrorOnUi("Enter at Least 6 Digit Password")
            return
        }
        attemptLoginUserToFirebase(
            firebaseAuth,
            emailInputEt.getText().toString(), passwordInputEt.getText().toString()
        )
    }

    private fun attemptLoginUserToFirebase(
        firebaseAuth: FirebaseAuth,
        emailInput: String,
        passwordInput: String
    ) {
        fireLoadingEvent(true)
        firebaseAuth.signInWithEmailAndPassword(
            emailInput, passwordInput
        ).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                firebaseAuth.currentUser?.uid?.let { attemptFetchUserBatchFromFirebase(it) }
            } else {
                fireMessageEvent(task.exception!!.localizedMessage)
                fireLoadingEvent(false)
            }
        }
    }

    private fun attemptFetchUserBatchFromFirebase(firebaseUid: String) {
        FirebaseDataRef.provideBatchRef()?.child(firebaseUid)?.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        try {
                            saveUserBatch(snapshot.getValue(String::class.java))
                        } catch (_: Exception) {
                            fireMessageEvent("Something went wrong")
                            fireLoadingEvent(false)
                        } finally {
                            attemptFetchUserProfileDataFrom(firebaseUid)
                        }
                    } else {
                        fireMessageEvent("Batch not found!")
                        fireLoadingEvent(false)
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    fireMessageEvent(error.message)
                    fireLoadingEvent(false)
                }
            }
        )
    }

    private fun attemptFetchUserProfileDataFrom(firebaseUid: String) {
        FirebaseDataRef.provideStudentRef()?.child(firebaseUid)?.addValueEventListener(
            object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        try {
                            saveUserProfile(snapshot.getValue(UserProfileData::class.java))
                        } catch (e: Exception) {
                            fireMessageEvent("Something went wrong")
                        } finally {
                            fireNavigateEvent(0, null)
                        }
                    } else {
                        fireMessageEvent("Something went wrong")
                    }
                    fireLoadingEvent(false)
                }

                override fun onCancelled(error: DatabaseError) {
                    fireMessageEvent(error.message)
                    fireLoadingEvent(false)
                }
            }
        )
    }
}