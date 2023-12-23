package com.example.LyfeRisk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button submitButton;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @SuppressLint({"SourceLockedOrientationActivity", "MissingInflatedId"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        nameEditText = findViewById(R.id.editTextTextName);
        emailEditText = findViewById(R.id.editTextTextEmailAddress);
        passwordEditText = findViewById(R.id.editTextTextPassword);
        confirmPasswordEditText = findViewById(R.id.editTextTextPassword2);
        submitButton = findViewById(R.id.registerBtn);

        submitButton.setOnClickListener(view -> submitRegistration());
    }

    private void submitRegistration() {
        String name = nameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (emptyInputSignup(name, email, password, confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "Please fill in all fields.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (invalidEmail(email)) {
            Toast.makeText(RegisterActivity.this, "Please enter a valid email address.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!passwordMatch(password, confirmPassword)) {
            Toast.makeText(RegisterActivity.this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!validPassword(password)) {
            Toast.makeText(RegisterActivity.this, "Password must contain:", Toast.LENGTH_LONG).show();
            Toast.makeText(RegisterActivity.this, "Min 8 chars, uppercase, number, special char.", Toast.LENGTH_LONG).show();
            return;
        }

        // Use Firebase Authentication to create a user with email and password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("RegisterActivity", "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();

                        // Store additional user information in the database
                        Map<String, Object> userValues = new HashMap<>();
                        userValues.put("userID", user.getUid());
                        userValues.put("name", name);
                        userValues.put("email", email);
                        userValues.put("isPaid", false);
                        userValues.put("auth", 0);
                        userValues.put("linkedDoctorIMC","");


                        // Use the user's UID as the key for the database entry
                        mDatabase.child("PatientUsers").child(user.getUid()).setValue(userValues)
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("RegisterActivity", "User data saved successfully.");
                                    Toast.makeText(RegisterActivity.this, "Registration successful.", Toast.LENGTH_SHORT).show();
                                    updateUI(user);
                                })
                                .addOnFailureListener(e -> {
                                    Log.e("RegisterActivity", "Failed to save user data", e);
                                    Toast.makeText(RegisterActivity.this, "Failed to save user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                });
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("RegisterActivity", "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }


    private void updateUI(FirebaseUser user) {
        if (user != null) {
            // User is signed in
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            // User is signed out
            Log.d("RegisterActivity", "User authentication failed.");
        }
    }

        private boolean emptyInputSignup(String name, String email, String password, String confirmPassword) {
        return name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty();
    }

    private boolean invalidEmail(String email) {
        return !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean passwordMatch(String password, String confirmPassword) {
        // The passwords match if they are equal to each other
        return password.equals(confirmPassword);
    }

    private boolean validPassword(String password) {
        Pattern passwordPattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,}$");
        return passwordPattern.matcher(password).matches();
    }
}
