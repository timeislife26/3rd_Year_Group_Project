package com.example.LyfeRisk;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MedicalActivity extends AppCompatActivity {

    private EditText medicalRecordsEditText;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);
        // Makes the app in portrait mode only
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        medicalRecordsEditText = findViewById(R.id.editTextTextMultiLine2);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("PatientUsers");
    }

    public void saveAndQuit(View view) {
        String medicalRecords = medicalRecordsEditText.getText().toString();
        String[] lines = medicalRecords.split("\r\n|\r|\n");

        if (lines.length > 6) {
            Toast.makeText(this, "Medical records can have a maximum of 6 lines", Toast.LENGTH_SHORT).show();
        } else {
            String userUid = mAuth.getCurrentUser().getUid();

            // Save the medical records to the database
            mDatabase.child(userUid).child("medicalRecords").setValue(medicalRecords);

            Toast.makeText(this, "Medical records saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
