package com.polok.eubmanagement.presentation.auth.signin;

import static com.polok.eubmanagement.util.Extension.showErrorOnUi;
import android.os.Bundle;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.base.model.OnNavigate;
import com.polok.eubmanagement.data.model.UserProfileData;
import com.polok.eubmanagement.firebase.FirebaseDataRef;
import com.polok.eubmanagement.util.FirebaseChildTag;
import com.polok.eubmanagement.util.SharedPref;

public class SignInViewModel extends BaseViewModel {
    private String isAdmin;

    void validateInputItemsAndExecuteSignup(
            EditText emailInputEt, EditText passwordInputEt, FirebaseAuth firebaseAuth
    ) {
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailInputEt.getText().toString()).matches()) {
            showErrorOnUi(emailInputEt, "Enter A Valid Email Address");
            return;
        }
        if (passwordInputEt.getText().toString().isEmpty() || passwordInputEt.getText().length() < 6) {
            showErrorOnUi(passwordInputEt, "Enter at Least 6 Digit Password");
            return;
        }
        attemptLoginUserToFirebase(
                firebaseAuth,
                emailInputEt.getText().toString(), passwordInputEt.getText().toString()
        );
    }

    private void attemptLoginUserToFirebase(FirebaseAuth firebaseAuth, String emailInput, String passwordInput) {
        fireLoadingEvent(true);
        firebaseAuth.signInWithEmailAndPassword(emailInput, passwordInput).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) attemptFetchUserBatchFromFirebase(firebaseAuth.getCurrentUser().getUid());
                else {
                    fireMessageEvent(task.getException().getLocalizedMessage());
                    fireLoadingEvent(false);
                }
            }
        });
    }

    private void attemptFetchUserBatchFromFirebase(String firebaseUid) {
        FirebaseDataRef.provideBatchRef().child(firebaseUid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) try {
                    SharedPref.saveUserBatch(snapshot.getValue(String.class));
                } catch (Exception ignored) {
                    fireMessageEvent("Something went wrong");
                    fireLoadingEvent(false);
                } finally {
                    attemptFetchUserProfileDataFrom(firebaseUid);
                } else {
                    fireMessageEvent("Batch not found!");
                    fireLoadingEvent(false);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                fireMessageEvent(error.getMessage());
                fireLoadingEvent(false);
            }
        });
    }

    private void attemptFetchUserProfileDataFrom(String firebaseUid) {
        FirebaseDataRef.provideStudentRef().child(firebaseUid).child(FirebaseChildTag.PROFILE.name()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) try {
                    UserProfileData userProfileData = snapshot.getValue(UserProfileData.class);
                    SharedPref.saveUserProfile(userProfileData);
                    isAdmin = userProfileData.getAdmin().toString();
                } catch (Exception ignored) {
                    fireMessageEvent("Something went wrong");
                } finally {
                    Bundle bundle = new Bundle();
                    bundle.putString("is_admin", isAdmin);
                    fireNavigateEvent(new OnNavigate(1, bundle));
                } else {
                    fireMessageEvent("Something went wrong");
                }
                fireLoadingEvent(false);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                fireMessageEvent(error.getMessage());
                fireLoadingEvent(false);
            }
        });
    }
}
