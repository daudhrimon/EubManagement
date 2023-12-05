package com.polok.eubmanagement.presentation.signin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.polok.eubmanagement.base.BaseActivity;
import com.polok.eubmanagement.base.BaseApp;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.data.model.UserProfileData;
import com.polok.eubmanagement.databinding.ActivitySigninBinding;
import com.polok.eubmanagement.presentation.DashboardActivity;
import com.polok.eubmanagement.presentation.signup.SignupActivity;
import com.polok.eubmanagement.util.SharedPref;
import com.polok.eubmanagement.util.Extension;

public class SignInActivity extends BaseActivity<ActivitySigninBinding> {
    @Override
    protected ActivitySigninBinding initViewBinding() {
        return ActivitySigninBinding.inflate(getLayoutInflater());
    }

    @Override
    protected BaseViewModel setViewModel() {
        return null;
    }

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        Extension.hideStatusBar(getWindow());

        binding.loginButton.setOnClickListener(view -> {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailInput.getText().toString()).matches()) {
                showError(binding.emailInput, "Enter A Valid Email Address");
                return;
            }
            if (binding.passwordInput.getText().toString().isEmpty() || binding.passwordInput.getText().length() < 6) {
                showError(binding.passwordInput, "Enter at Least 6 Digit Password");
                return;
            }
            attemptLoginWithFirebase();
        });
        binding.forgotPassword.setOnClickListener(view -> {

        });
        binding.signupButton.setOnClickListener(view -> {
            startActivity(new Intent(this, SignupActivity.class));
        });
        binding.emailInput.setText("daud@abc.com");
        binding.passwordInput.setText("123456");
    }

    private void attemptLoginWithFirebase() {
        binding.primaryLoader.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().signInWithEmailAndPassword(
                binding.emailInput.getText().toString(),
                binding.passwordInput.getText().toString()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    attemptFetchUserData();
                } else {
                    Extension.showToast(SignInActivity.this,task.getException().getMessage());
                    binding.primaryLoader.setVisibility(View.GONE);
                }
            }
        });
    }

    private void attemptFetchUserData() {
        BaseApp.getFirebaseDataRef().child("Info").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserProfileData userProfileData = snapshot.getValue(UserProfileData.class);
                    Log.wtf("DATA",new Gson().toJson(userProfileData));
                    if (userProfileData != null) {
                        SharedPref.init(SignInActivity.this);
                        SharedPref.saveUserData(userProfileData);
                        binding.primaryLoader.setVisibility(View.GONE);
                        Intent intent = new Intent(SignInActivity.this, DashboardActivity.class);
                        if (userProfileData.getAdmin() != null && userProfileData.getAdmin()) {
                            intent.putExtra("is_admin","true");
                        } else {
                            intent.putExtra("is_admin","false");
                        }
                        startActivity(intent);
                        finish();
                    } else {
                        Extension.showToast(SignInActivity.this,"Something went wrong");
                        binding.primaryLoader.setVisibility(View.GONE);
                    }
                } else {
                    Extension.showToast(SignInActivity.this,"Something went wrong");
                    binding.primaryLoader.setVisibility(View.GONE);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void showError(EditText inputField, String errorMessage) {
        inputField.setError(errorMessage);
        inputField.requestFocus();
    }
}