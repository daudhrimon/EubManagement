package com.polok.eubmanagement.presentation.auth.signup

import android.util.Patterns
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.polok.eubmanagement.base.BaseViewModel
import com.polok.eubmanagement.firebase.FirebaseDataRef
import com.polok.eubmanagement.model.UserProfileData
import com.polok.eubmanagement.util.SharedPref
import com.polok.eubmanagement.util.showErrorOnUi

class SignUpViewModel : BaseViewModel() {
    var gender: String? = null
    var batch: String? = null
    var section: String? = null
    var bloodGroup: String? = null
    var genderZero: String? = null
    var batchZero: String? = null
    var sectionZero: String? = null
    var bloodGroupZero: String? = null

    fun validateInputItemsAndExecuteSignup(
        studentIdEt: EditText,
        fullNameEt: EditText,
        mobileInputEt: EditText,
        emailInputEt: EditText,
        passwordInputEt: EditText,
        firebaseAuth: FirebaseAuth
    ) {
        if (studentIdEt.getText().toString().isEmpty()) {
            studentIdEt.showErrorOnUi("Enter Student ID")
            return
        }
        if (fullNameEt.getText().toString().isEmpty()) {
            fullNameEt.showErrorOnUi("Enter Full Name")
            return
        }
        if (mobileInputEt.getText().toString().isEmpty()) {
            mobileInputEt.showErrorOnUi("Enter Mobile Number")
            return
        }
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
        if (gender == null || gender!!.isEmpty() || gender == genderZero) {
            fireMessageEvent(genderZero)
            return
        }
        if (batch == null || batch?.isEmpty() == true || batch == batchZero) {
            fireMessageEvent(batchZero)
            return
        }
        if (section == null || section?.isEmpty() == true || section == sectionZero) {
            fireMessageEvent(sectionZero)
            return
        }
        if (bloodGroup == null || bloodGroup?.isEmpty() == true || bloodGroup == bloodGroupZero) {
            fireMessageEvent(bloodGroupZero)
            return
        }
        attemptSignupUserToFirebase(
            firebaseAuth,
            studentIdEt.getText().toString(),
            fullNameEt.getText().toString(),
            mobileInputEt.getText().toString(),
            emailInputEt.getText().toString(),
            passwordInputEt.getText().toString()
        )
    }

    private fun attemptSignupUserToFirebase(
        firebaseAuth: FirebaseAuth,
        studentId: String,
        fullName: String,
        mobileInput: String,
        emailInput: String,
        passwordInput: String
    ) {
        fireLoadingEvent(true)
        firebaseAuth.createUserWithEmailAndPassword(emailInput, passwordInput)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    firebaseAuth.currentUser?.uid?.let {
                        attemptPostUserBatchToFirebase(
                            it, studentId, fullName, mobileInput, emailInput
                        )
                    }
                } else {
                    fireMessageEvent(task.exception!!.localizedMessage)
                    fireLoadingEvent(false)
                }
            }
    }

    private fun attemptPostUserBatchToFirebase(
        firebaseUid: String,
        studentId: String, fullName: String, mobileInput: String, emailInput: String
    ) {
        FirebaseDataRef.provideBatchRef()?.child(firebaseUid)?.setValue(batch)
            ?.addOnCompleteListener { task ->
                if (task.isComplete) {
                    try {
                        SharedPref.saveUserBatch(batch)
                    } catch (ignored: Exception) {
                        fireMessageEvent("Something went wrong")
                        fireLoadingEvent(false)
                    } finally {
                        attemptPostUserProfileDataToFirebase(
                            firebaseUid,
                            studentId,
                            fullName,
                            mobileInput,
                            emailInput
                        )
                    }
                } else {
                    fireMessageEvent(task.exception?.localizedMessage)
                    fireLoadingEvent(false)
                }
            }
    }

    private fun attemptPostUserProfileDataToFirebase(
        firebaseUid: String,
        studentId: String, fullName: String, mobileInput: String, emailInput: String
    ) {
        val userProfileData = UserProfileData(
            studentId,
            fullName,
            mobileInput,
            emailInput,
            gender,
            section,
            bloodGroup,
            false
        )
        FirebaseDataRef.provideStudentRef()?.child(firebaseUid)?.setValue(userProfileData)
            ?.addOnCompleteListener { task ->
                if (task.isComplete) {
                    fireMessageEvent("Sign up Successfully")
                    fireNavigateEvent(0, null)
                } else {
                    fireMessageEvent(task.exception?.localizedMessage)
                }
                fireLoadingEvent(false)
            }
    }
}