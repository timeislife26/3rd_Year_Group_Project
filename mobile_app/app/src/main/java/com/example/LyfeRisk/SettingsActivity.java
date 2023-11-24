package com.example.LyfeRisk;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class SettingsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("PatientUsers");

        // Add click listeners to your authentication buttons
        Button biometricsButton = findViewById(R.id.biometricsButton);
        Button emailButton = findViewById(R.id.emailButton);
        Button no2faButton = findViewById(R.id.no2faButton);
        Button authAppButton = findViewById(R.id.authAppButton);

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
                manageAuth(2);
            }
        });

        authAppButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageAuth(3);
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


    public void saveAndQuit(View view) {
        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
