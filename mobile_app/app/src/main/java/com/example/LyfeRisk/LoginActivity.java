package com.example.LyfeRisk;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.concurrent.TimeUnit;


public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private boolean isEmailVerificationButtonEnabled = true;
    private BiometricPrompt biometricPrompt;
    private BiometricPrompt.PromptInfo promptInfo;

    private EditText phoneNumberEditText;
    private Button sendCodeButton;
    private String verificationId;


    @SuppressLint({"SourceLockedOrientationActivity", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("PatientUsers");

        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        loginButton = findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(view -> attemptLogin());

        Intent intent = getIntent();
        if (intent != null && intent.getData() != null) {
            handleDynamicLink(intent.getData());
        }
    }
    private void attemptLogin() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Please enter both email and password.", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success
                        FirebaseUser user = mAuth.getCurrentUser();

                        checkAuthLevel(user.getUid());

                    } else {
                        // If sign in fails, display a message to the user.
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

        biometricPrompt = new BiometricPrompt(this, ContextCompat.getMainExecutor(this),
                new BiometricPrompt.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationError(int errorCode, CharSequence errString) {
                        super.onAuthenticationError(errorCode, errString);
                    }

                    @Override
                    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        // Handle authentication success
                        goToMainMenu();
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        // Handle authentication failure
                        Toast.makeText(LoginActivity.this, "Biometric authentication failed", Toast.LENGTH_SHORT).show();
                    }
                });

        // Customize promptInfo for different biometric methods
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Biometric login")
                .setSubtitle("Authenticate")
                .setNegativeButtonText("Cancel")
                .build();
    }

    private void checkAuthLevel(String userUid) {
        // Retrieve the authentication level from the database
        mDatabase.child(userUid).child("auth").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    int authLevel = dataSnapshot.getValue(Integer.class);
                    performActionBasedOnAuthLevel(authLevel);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void performActionBasedOnAuthLevel(int authLevel) {
        switch (authLevel) {
            case 0:
                goToMainMenu();
                break;
            case 1:
                sendEmailVerification();
                break;
            case 2:
                performBiometricAuthentication();
                break;
            default:
                break;
        }
    }

    private void goToMainMenu() {
        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }


    private void performBiometricAuthentication() {
        // Display biometric authentication prompt
        biometricPrompt.authenticate(promptInfo);
    }

    private void sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null && isEmailVerificationButtonEnabled) {
            String email = user.getEmail();

            // Disable the button to prevent multiple clicks
            isEmailVerificationButtonEnabled = false;

            // Build the email link without a verification code
            String emailLink = "https://lyferisk.page.link/Email";

            ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                    .setUrl(emailLink)
                    .setHandleCodeInApp(true)
                    .setAndroidPackageName(
                            "com.example.LyfeRisk",
                            false,
                            "12" )
                    .build();

            mAuth.sendSignInLinkToEmail(email, actionCodeSettings)
                    .addOnCompleteListener(this, task -> {
                        // Enable the button regardless of the result to allow future attempts
                        isEmailVerificationButtonEnabled = true;

                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login link sent to your email", Toast.LENGTH_SHORT).show();
                            // Now, when the user opens the link, you can log them in directly
                        } else {
                            Toast.makeText(LoginActivity.this, "Failed to send login link", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


private void handleDynamicLink(Uri data) {
    Log.d("DynamicLink", "Handling dynamic link: " + data.toString());

    mAuth.signInWithEmailLink(mAuth.getCurrentUser().getEmail(), data.toString())
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Sign in success
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        // Now, you can proceed to the MenuActivity
                        Log.d("DynamicLink", "Sign in successful. Opening MenuActivity.");
                        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
                        startActivity(intent);
                        finish(); // Optional: Close the LoginActivity if needed
                    } else {
                        Log.e("DynamicLink", "Failed to sign in: User is null.");
                        Toast.makeText(LoginActivity.this, "Failed to sign in", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // If sign-in fails, display a message to the user.
                    Log.e("DynamicLink", "Failed to sign in with email link.", task.getException());
                    Toast.makeText(LoginActivity.this, "Failed to sign in with email link.", Toast.LENGTH_SHORT).show();
                }
            });
}
}