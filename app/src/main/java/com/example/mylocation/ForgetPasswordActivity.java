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

import com.example.mylocation.databinding.ActivityForgetPasswordBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ForgetPasswordActivity extends AppCompatActivity {

    private ActivityForgetPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgetPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.btnSendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.forgetEditTextEmail.getText().toString();
                if (!isValidEmail(binding.forgetEditTextEmail.getText().toString())) {
                    binding.forgetTextInputLayoutEmail.setError(getString(R.string.invalid_email));
                    return;
                }
                sendEmail(email);
            }
        });

        textWatcher();

    }

    private void sendEmail(String email) {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetPasswordActivity.this, "Email Sent", Toast.LENGTH_SHORT).show();
                            Log.d("myTag", "Email sent.");
                            startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                        } else {
                            Toast.makeText(ForgetPasswordActivity.this, "Email sending fail, Something went Wrong try after sometime", Toast.LENGTH_SHORT).show();
                            Log.d("myTag", "Email sending fail.");
                            startActivity(new Intent(ForgetPasswordActivity.this, LoginActivity.class));
                        }
                    }
                });
    }

    private boolean isValidEmail(String email) {
        // Check if email is empty
        if (TextUtils.isEmpty(email)) {
            return false;
        }

        // Check if email is valid using regex pattern
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void textWatcher() {
        // Set up text change listener for email field
        binding.forgetEditTextEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                binding.forgetTextInputLayoutEmail.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
}