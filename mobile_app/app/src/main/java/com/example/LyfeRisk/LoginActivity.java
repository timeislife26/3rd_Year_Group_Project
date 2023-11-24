package com.example.LyfeRisk;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;


public class LoginActivity extends AppCompatActivity {

    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;




    @SuppressLint("SourceLockedOrientationActivity")
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
            // User opened the link, handle the authentication
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
                // Handle error
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
                Toast.makeText(this, "BIOMETRICS", Toast.LENGTH_SHORT).show();
                goToMainMenu();
                break;
            case 3:
                Toast.makeText(this, "AUTH APP", Toast.LENGTH_SHORT).show();
                goToMainMenu();
                break;
            // Add cases for other auth levels as needed
            default:
                // Handle default case or unexpected auth levels
                break;
        }
    }

    private void goToMainMenu() {
        Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(LoginActivity.this, MenuActivity.class);
        startActivity(intent);
        finish();
    }

    private void sendEmailVerification() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            String email = user.getEmail();

            // Build the email link without a verification code
            String emailLink = "https://lyferisk.page.link/Email";

            ActionCodeSettings actionCodeSettings = ActionCodeSettings.newBuilder()
                    .setUrl(emailLink)
                    .setHandleCodeInApp(true)
                    .setAndroidPackageName(
                            "com.example.LyfeRisk",
                            false, /* installIfNotAvailable */
                            "12" /* minimumVersion */)
                    .build();

            mAuth.sendSignInLinkToEmail(email, actionCodeSettings)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login link sent to your email", Toast.LENGTH_SHORT).show();
                            // Now, when the user opens the link, you can log them in directly
                        } else {
                            Toast.makeText(LoginActivity.this, "Failed to send login link", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


/*
    private void show2faDialog() {
        // Create a dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.activity_authemail);

        EditText codeEditText = dialog.findViewById(R.id.codeEditText);
        Button sendTextButton = dialog.findViewById(R.id.sendTextButton);

        sendTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Dismiss the dialog
                dialog.dismiss();
            }
        });

        // Show the dialog
        dialog.show();
    }*/
private void handleDynamicLink(Uri data) {
    mAuth.checkActionCode(data.toString())
            .addOnCompleteListener(this, task -> {
                if (task.isSuccessful()) {
                    // Link is valid
                    mAuth.applyActionCode(data.toString())
                            .addOnCompleteListener(this, applyTask -> {
                                if (applyTask.isSuccessful()) {
                                    // Sign in success
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    if (user != null) {
                                        // Now, you can proceed to the MenuActivity
                                        goToMainMenu();
                                    } else {
                                        Toast.makeText(LoginActivity.this, "Failed to sign in", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    // If applyActionCode fails, display a message to the user.
                                    Toast.makeText(LoginActivity.this, "Failed to apply action code.", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    // If checkActionCode fails, display a message to the user.
                    Toast.makeText(LoginActivity.this, "Invalid action code.", Toast.LENGTH_SHORT).show();
                }
            });
}
}