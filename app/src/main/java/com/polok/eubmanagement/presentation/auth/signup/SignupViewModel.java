package com.polok.eubmanagement.presentation.auth.signup;

import static com.polok.eubmanagement.util.Extension.showErrorOnUi;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.base.model.OnNavigate;
import com.polok.eubmanagement.data.model.UserProfileData;
import com.polok.eubmanagement.firebase.FirebaseDataRef;
import com.polok.eubmanagement.util.FirebaseChildTag;
import com.polok.eubmanagement.util.SharedPref;

public class SignupViewModel extends BaseViewModel {
    String gender, batch, section, bloodGroup;
    String genderZero, batchZero, sectionZero, bloodGroupZero;


    void validateInputItemsAndExecuteSignup(
            EditText studentIdEt, EditText fullNameEt, EditText mobileInputEt, EditText emailInputEt, EditText passwordInputEt,
            FirebaseAuth firebaseAuth
    ) {
        if (studentIdEt.getText().toString().isEmpty()) {
            showErrorOnUi(studentIdEt, "Enter Student ID");
            return;
        }
        if (fullNameEt.getText().toString().isEmpty()) {
            showErrorOnUi(fullNameEt, "Enter Full Name");
            return;
        }
        if (mobileInputEt.getText().toString().isEmpty()) {
            showErrorOnUi(mobileInputEt, "Enter Mobile Number");
            return;
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInputEt.getText().toString()).matches()) {
            showErrorOnUi(emailInputEt, "Enter A Valid Email Address");
            return;
        }
        if (passwordInputEt.getText().toString().isEmpty() || passwordInputEt.getText().length() < 6) {
            showErrorOnUi(passwordInputEt, "Enter at Least 6 Digit Password");
            return;
        }
        if (gender == null || gender.isEmpty() || gender.equals(genderZero)) {
            fireMessageEvent(genderZero);
            return;
        }
        if (batch == null || batch.isEmpty() || batch.equals(batchZero)) {
            fireMessageEvent(batchZero);
            return;
        }
        if (section == null || section.isEmpty() || section.equals(sectionZero)) {
            fireMessageEvent(sectionZero);
            return;
        }
        if (bloodGroup == null || bloodGroup.isEmpty() || bloodGroup.equals(bloodGroupZero)) {
            fireMessageEvent(bloodGroupZero);
            return;
        }
        attemptSignupUserToFirebase(
                firebaseAuth,
                studentIdEt.getText().toString(),
                fullNameEt.getText().toString(),
                mobileInputEt.getText().toString(),
                emailInputEt.getText().toString(),
                passwordInputEt.getText().toString()
        );
    }

    private void attemptSignupUserToFirebase(
            FirebaseAuth firebaseAuth,
            String studentId, String fullName, String mobileInput, String emailInput, String passwordInput
    ) {
        fireLoadingEvent(true);
        firebaseAuth.createUserWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    attemptPostUserBatchToFirebase(
                            firebaseAuth.getCurrentUser().getUid(), studentId,fullName,mobileInput,emailInput,passwordInput
                    );
                }
                else {
                    fireMessageEvent(task.getException().getLocalizedMessage());
                    fireLoadingEvent(false);
                }
            }
        });
    }

    private void attemptPostUserBatchToFirebase(
            String firebaseUid,
            String studentId, String fullName, String mobileInput, String emailInput, String passwordInput
    ) {
        //HashMap<String, String> userBatchMap = new HashMap<>();
        FirebaseDataRef.provideBatchRef().child(firebaseUid).setValue(batch).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) try {
                    SharedPref.saveUserBatch(batch);
                } catch (Exception ignored) {
                    fireMessageEvent("Something went wrong");
                    fireLoadingEvent(false);
                } finally {
                    attemptPostUserProfileDataToFirebase(firebaseUid,studentId,fullName,mobileInput,emailInput,passwordInput);
                } else {
                    fireMessageEvent(task.getException().getLocalizedMessage());
                    fireLoadingEvent(false);
                }
            }
        });
    }

    private void attemptPostUserProfileDataToFirebase(
            String firebaseUid,
            String studentId, String fullName, String mobileInput, String emailInput, String passwordInput
    ) {
            UserProfileData userProfileData = new UserProfileData(studentId, fullName, mobileInput, emailInput, gender, section, bloodGroup, false);
            FirebaseDataRef.provideStudentRef().child(firebaseUid).child(FirebaseChildTag.PROFILE.name()).setValue(userProfileData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isComplete()) {
                        fireMessageEvent("Signup Successfully");
                        fireNavigateEvent(new OnNavigate(1));
                    } else fireMessageEvent(task.getException().getLocalizedMessage());
                    fireLoadingEvent(false);
                }
            });
    }
}
