package com.example.LyfeRisk;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;
import androidx.biometric.BiometricPrompt;
import androidx.core.content.ContextCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SettingsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("PatientUsers");

        // Add click listeners to your authentication buttons
        Button emailButton = findViewById(R.id.emailButton);
        Button no2faButton = findViewById(R.id.no2faButton);
        Button biometricsButton = findViewById(R.id.biometricsButton);

        no2faButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageAuth(0);
            }
        });

        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageAuth(1);
            }
        });

        biometricsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isBiometricAvailable()) {
                    manageAuth(2);
                } else {
                    Toast.makeText(SettingsActivity.this, "Biometrics not available on this device", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void manageAuth(int authType) {
        if (mAuth.getCurrentUser() != null) {
            // Get the currently logged-in user's UID
            String userUid = mAuth.getCurrentUser().getUid();

            // Update the authentication method in the "auth" node of the user's data in the database
            mDatabase.child(userUid).child("auth").setValue(authType);

            Toast.makeText(this, "Authentication method updated", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isBiometricAvailable() {
        BiometricManager biometricManager = BiometricManager.from(this);
        return biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS;
    }

    public void saveAndQuit(View view) {
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}