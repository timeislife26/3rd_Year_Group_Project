package com.example.LyfeRisk;
import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RecordsActivity extends AppCompatActivity {

    private Spinner spinnerOptions, spinnerDetails;
    private TextView infoTextView;
    private Button saveButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        // Makes the app in portrait mode only
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        spinnerOptions = findViewById(R.id.spinnerOptions);
        spinnerDetails = findViewById(R.id.spinnerDetails);
        infoTextView = findViewById(R.id.infoTextView);
        saveButton = findViewById(R.id.saveButton);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOptions.setAdapter(adapter);

        spinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedOption = spinnerOptions.getSelectedItem().toString();

                ArrayAdapter<CharSequence> detailsAdapter = null;

                // Check if "Patient Info" is selected
                if ("Patient Info".equals(selectedOption)) {
                    infoTextView.setVisibility(View.VISIBLE);
                    saveButton.setVisibility(View.VISIBLE);
                    spinnerDetails.setVisibility(View.GONE);

                    if (mAuth.getCurrentUser() != null) {
                        // Get the currently logged-in user's UID
                        String userUid = mAuth.getCurrentUser().getUid();

                        // Retrieve the patient's medical history from the database
                        mDatabase.child(userUid).child("medicalRecords").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String medicalRecords = dataSnapshot.getValue(String.class);
                                    // Display the retrieved medical history
                                    infoTextView.setText("Medical History:\n" + medicalRecords);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                        // Retrieve the doctor information
                        mDatabase.child(userUid).child("Doctor").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String Doctor = dataSnapshot.getValue(String.class);
                                    // Append the doctor information
                                    infoTextView.append("\nDoctor: " + Doctor);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                        // Retrieve the insurance company information
                        mDatabase.child(userUid).child("Insurance").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String Insurance
                                            = dataSnapshot.getValue(String.class);
                                    // Append the insurance company information
                                    infoTextView.append("\nInsurance Company: " + Insurance
                                    );
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }

                } else if ("Doctor".equals(selectedOption)) {
                    // Show the save button and populate spinnerDetails with "Doctor" options
                    infoTextView.setVisibility(View.GONE);
                    saveButton.setVisibility(View.VISIBLE);
                    // Create an ArrayAdapter with doctor names
                    detailsAdapter = ArrayAdapter.createFromResource(RecordsActivity.this, R.array.doctor_names, android.R.layout.simple_spinner_item);
                    spinnerDetails.setVisibility(View.VISIBLE);
                } else if ("Insurance".equals(selectedOption)) {
                    // Show the save button and populate spinnerDetails with "Insurance" options
                    infoTextView.setVisibility(View.GONE);
                    saveButton.setVisibility(View.VISIBLE);
                    // Create an ArrayAdapter with insurance companies
                    detailsAdapter = ArrayAdapter.createFromResource(RecordsActivity.this, R.array.insurance_companies, android.R.layout.simple_spinner_item);
                    spinnerDetails.setVisibility(View.VISIBLE);
                } else {
                    // If any other option is selected, hide the save button and the spinnerDetails
                    infoTextView.setVisibility(View.GONE);
                    saveButton.setVisibility(View.GONE);
                    spinnerDetails.setVisibility(View.GONE);
                }

                if (detailsAdapter != null) {
                    detailsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerDetails.setAdapter(detailsAdapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        saveButton.setOnClickListener(view -> {
            String selectedOption = spinnerOptions.getSelectedItem().toString();
            String selectedDetails = spinnerDetails.getSelectedItem().toString();

            if (mAuth.getCurrentUser() != null) {
                // Get the currently logged-in user's UID
                String userUid = mAuth.getCurrentUser().getUid();

                // Save the selected information to the user's data in the database
                mDatabase.child(userUid).child(selectedOption).setValue(selectedDetails);

                // Inform the user that data is saved
                String message = "Data saved for " + selectedOption + ": " + selectedDetails;
                infoTextView.setText(message);

                // Show a toast message to inform the user
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
