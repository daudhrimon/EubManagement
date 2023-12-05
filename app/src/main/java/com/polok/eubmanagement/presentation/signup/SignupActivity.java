package com.polok.eubmanagement.presentation.signup;

import static com.polok.eubmanagement.util.Extension.showErrorOnUi;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.polok.eubmanagement.base.BaseActivity;
import com.polok.eubmanagement.base.BaseApp;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.data.model.UserProfileData;
import com.polok.eubmanagement.databinding.ActivitySignupBinding;
import com.polok.eubmanagement.util.Extension;

import java.util.Objects;

public class SignupActivity extends BaseActivity<ActivitySignupBinding> {
    @Override
    protected ActivitySignupBinding initViewBinding() {
        return ActivitySignupBinding.inflate(getLayoutInflater());
    }
    private String gender, batch, section, bloodGroup;
    private String genderZero, batchZero, sectionZero, bloodGroupZero;

    @Override
    protected BaseViewModel setViewModel() {
        return null;
    }

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
        Extension.hideStatusBar(getWindow());
        genderZero = getResources().getStringArray(R.array.gender_spinner)[0];
        batchZero = getResources().getStringArray(R.array.batch_spinner)[0];
        sectionZero = getResources().getStringArray(R.array.section_spinner)[0];
        bloodGroupZero = getResources().getStringArray(R.array.blood_group_spinner)[0];

        binding.genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                gender = getResources().getStringArray(R.array.gender_spinner)[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        binding.batchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                batch = getResources().getStringArray(R.array.batch_spinner)[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        binding.sectionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                section = getResources().getStringArray(R.array.section_spinner)[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });
        binding.bloodGroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int pos, long l) {
                bloodGroup = getResources().getStringArray(R.array.blood_group_spinner)[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        binding.signupButton.setOnClickListener(view -> {
            if (binding.studentId.getText().toString().isEmpty()) {
                showErrorOnUi(binding.studentId, "Enter Student ID");
                return;
            }
            if (binding.fullName.getText().toString().isEmpty()) {
                showErrorOnUi(binding.fullName, "Enter Full Name");
                return;
            }
            if (binding.mobileInput.getText().toString().isEmpty()) {
                showErrorOnUi(binding.mobileInput, "Enter Mobile Number");
                return;
            }
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailInput.getText().toString()).matches()) {
                showErrorOnUi(binding.emailInput, "Enter A Valid Email Address");
                return;
            }
            if (binding.passwordInput.getText().toString().isEmpty() || binding.passwordInput.getText().length() < 6) {
                showErrorOnUi(binding.passwordInput, "Enter at Least 6 Digit Password");
                return;
            }
            if (gender == null || gender.isEmpty() || gender.equals(genderZero)) {
                Extension.showToast(this, genderZero);
                return;
            }
            if (batch == null || batch.isEmpty() || batch.equals(batchZero)) {
                Extension.showToast(this, batchZero);
                return;
            }
            if (section == null || section.isEmpty() || section.equals(sectionZero)) {
                Extension.showToast(this, sectionZero);
                return;
            }
            if (bloodGroup == null || bloodGroup.isEmpty() || bloodGroup.equals(bloodGroupZero)) {
                Extension.showToast(this, bloodGroupZero);
                return;
            }
            attemptSignupWithFireBase();
        });
    }

    private void attemptSignupWithFireBase() {
        binding.primaryLoader.setVisibility(View.VISIBLE);
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(
                binding.emailInput.getText().toString(), binding.passwordInput.getText().toString()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    attemptPostUserDataToFirebase();
                } else {
                    Extension.showToast(SignupActivity.this, Objects.requireNonNull(task.getException()).getMessage());
                    binding.primaryLoader.setVisibility(View.GONE);
                }
            }
        });
    }

    private void attemptPostUserDataToFirebase() {
        UserProfileData userProfileData = new UserProfileData(
                binding.studentId.getText().toString(),
                binding.fullName.getText().toString(),
                binding.mobileInput.getText().toString(),
                binding.emailInput.getText().toString(),
                gender, batch, section, bloodGroup, false
        );
        BaseApp.getFirebaseDataRef().child("Info").setValue(userProfileData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()) {
                    binding.primaryLoader.setVisibility(View.GONE);
                    Extension.showToast(SignupActivity.this,"Signup Successful");
                    finish();
                } else {
                    Extension.showToast(SignupActivity.this, Objects.requireNonNull(task.getException()).getMessage());
                    binding.primaryLoader.setVisibility(View.GONE);
                }
            }
        });
    }
}