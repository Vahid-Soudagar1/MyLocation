package com.example.mylocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.mylocation.databinding.ActivityRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {


    private ActivityRegisterBinding binding;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        binding.btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isValidEmail(binding.editTextEmail.getText().toString())) {
                    binding.textInputLayoutEmail.setError(getString(R.string.invalid_email));
                    return;
                }

                if (!isValidPassword(binding.editTextPassword.getText().toString())) {
                    binding.textInputLayoutPassword.setError(getString(R.string.invalid_password));
                    return;
                }

                if (!isValidConfirmPassword(binding.editTextPassword.getText().toString(), binding.editTextConfirmPassword.getText().toString())) {
                    binding.textInputLayoutConfirmPassword.setError(getString(R.string.invalid_confirm_password));
                    return;
                }

                Toast.makeText(RegisterActivity.this, "Register SuccessFull", Toast.LENGTH_SHORT).show();
                String email = binding.editTextEmail.getText().toString();
                String password = binding.editTextPassword.getText().toString();
                registerUser(email, password);
            }
        });

        textWatcher();

        binding.alreadyUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

    }

    private void registerUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, store email and password in database and go to home activity
                            Log.d("myTag", "createUserWithEmail:success");
                            String userId = firebaseAuth.getCurrentUser().getUid();
                            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId);
                            Map<String, Object> user = new HashMap<>();
                            user.put("email", email);
                            user.put("password", password);
                            databaseReference.setValue(user);
                            Intent intent = new Intent(RegisterActivity.this, MapsActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("myTag", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void textWatcher() {
        // Set up text change listener for email field
        binding.editTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.textInputLayoutEmail.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        binding.editTextPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.textInputLayoutPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        // Set up text change listener for confirm password field
        binding.editTextConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.textInputLayoutConfirmPassword.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

    }


    // Method to validate email field
    private boolean isValidEmail(String email) {
        // Check if email is empty
        if (TextUtils.isEmpty(email)) {
            return false;
        }

        // Check if email is valid using regex pattern
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password) {
        // Check if password is empty
        if (TextUtils.isEmpty(password)) {
            return false;
        }

        // Check if password is at least 8 characters long
        return password.length() >= 8;
    }

    private boolean isValidConfirmPassword(String password, String confirmPassword) {
        // Check if confirm password is empty
        if (TextUtils.isEmpty(confirmPassword)) {
            return false;
        }

        // Check if confirm password matches password
        return password.equals(confirmPassword);
    }


}