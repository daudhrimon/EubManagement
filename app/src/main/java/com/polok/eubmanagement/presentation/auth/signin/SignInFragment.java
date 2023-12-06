package com.polok.eubmanagement.presentation.auth.signin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.polok.eubmanagement.R;
import com.polok.eubmanagement.base.BaseApp;
import com.polok.eubmanagement.base.BaseFragment;
import com.polok.eubmanagement.base.BaseViewModel;
import com.polok.eubmanagement.data.model.UserProfileData;
import com.polok.eubmanagement.databinding.FragmentSigninBinding;
import com.polok.eubmanagement.firebase.FirebaseDataRef;
import com.polok.eubmanagement.presentation.home.DashboardActivity;
import com.polok.eubmanagement.util.FirebaseChildTag;
import com.polok.eubmanagement.util.SharedPref;
import com.polok.eubmanagement.util.Extension;
import com.polok.eubmanagement.widget.PrimaryLoader;

public class SignInFragment extends BaseFragment<FragmentSigninBinding> {
    @Override
    protected FragmentSigninBinding initViewBinding() {
        return FragmentSigninBinding.inflate(getLayoutInflater());
    }
    private SignInViewModel viewModel;
    @Override
    protected BaseViewModel initViewModel() {
        return viewModel = new ViewModelProvider(this).get(SignInViewModel.class);
    }

    @Override
    protected void initOnCreateView(Bundle savedInstanceState) {
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
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_signInFragment_to_signupFragment);
        });
        binding.emailInput.setText("daud@daud.com");
        binding.passwordInput.setText("123456");
    }

    @Override
    protected PrimaryLoader initPrimaryLoader() {return binding.primaryLoader;}

    private void attemptLoginWithFirebase() {
        binding.primaryLoader.setVisibility(View.VISIBLE);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithEmailAndPassword(
                binding.emailInput.getText().toString(),
                binding.passwordInput.getText().toString()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    attemptFetchUserData(firebaseAuth.getCurrentUser().getUid());
                } else {
                    Extension.showToast(getContext(),task.getException().getMessage());
                    binding.primaryLoader.setVisibility(View.GONE);
                }
            }
        });
    }

    private void attemptFetchUserData(String firebaseUid) {
        FirebaseDataRef.provideStudentRef().child(firebaseUid).child(FirebaseChildTag.PROFILE.name()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    UserProfileData userProfileData = snapshot.getValue(UserProfileData.class);
                    Log.wtf("DATA", new Gson().toJson(userProfileData));
                    if (userProfileData != null) {
                        SharedPref.init(getContext());
                        SharedPref.saveUserData(userProfileData);
                        binding.primaryLoader.setVisibility(View.GONE);
                        Intent intent = new Intent(getContext(), DashboardActivity.class);
                        if (userProfileData.getAdmin() != null && userProfileData.getAdmin()) {
                            intent.putExtra("is_admin", "true");
                        } else {
                            intent.putExtra("is_admin", "false");
                        }
                        startActivity(intent);
                        getActivity().finish();
                    } else {
                        Extension.showToast(getContext(), "Something went wrong");
                        binding.primaryLoader.setVisibility(View.GONE);
                    }
                } else {
                    Extension.showToast(getContext(), "Something went wrong");
                    binding.primaryLoader.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void showError(EditText inputField, String errorMessage) {
        inputField.setError(errorMessage);
        inputField.requestFocus();
    }
}