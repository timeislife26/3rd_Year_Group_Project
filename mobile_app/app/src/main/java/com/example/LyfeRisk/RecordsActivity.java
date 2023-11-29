package com.example.LyfeRisk;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
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
    private DatabaseReference mDatabase, mMedicalDatabase;


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

        infoTextView.setMovementMethod(new ScrollingMovementMethod());

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("PatientUsers");
        mMedicalDatabase = FirebaseDatabase.getInstance().getReference("MedicalRecords");

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
                        mMedicalDatabase.child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    // Retrieve all medical record fields directly
                                    int age = dataSnapshot.child("Age").getValue(Integer.class);
                                    boolean gender = dataSnapshot.child("gender").getValue(Boolean.class);
                                    int cp = dataSnapshot.child("cp").getValue(Integer.class);
                                    int trewstbps = dataSnapshot.child("trewstbps").getValue(Integer.class);
                                    int chol = dataSnapshot.child("chol").getValue(Integer.class);
                                    int fbs = dataSnapshot.child("fbs").getValue(Integer.class);
                                    int restecg = dataSnapshot.child("restecg").getValue(Integer.class);
                                    int thalach = dataSnapshot.child("thalach").getValue(Integer.class);
                                    boolean exang = dataSnapshot.child("exang").getValue(Boolean.class);
                                    double oldpeak = dataSnapshot.child("oldpeak").getValue(Double.class);
                                    int slope = dataSnapshot.child("slope").getValue(Integer.class);
                                    int ca = dataSnapshot.child("ca").getValue(Integer.class);
                                    int thal = dataSnapshot.child("thal").getValue(Integer.class);
                                    boolean smoking = dataSnapshot.child("Smoking").getValue(Boolean.class);
                                    boolean yellowFingers = dataSnapshot.child("Yellow_Fingers").getValue(Boolean.class);
                                    boolean anxiety = dataSnapshot.child("Anxiety").getValue(Boolean.class);
                                    boolean chronicDisease = dataSnapshot.child("Chronic_Disease").getValue(Boolean.class);
                                    boolean fatigue = dataSnapshot.child("Fatigue").getValue(Boolean.class);
                                    boolean allergy = dataSnapshot.child("Allergy").getValue(Boolean.class);
                                    boolean wheezing = dataSnapshot.child("Wheezing").getValue(Boolean.class);
                                    boolean swallowingDifficulty = dataSnapshot.child("Swallowing_Difficulty").getValue(Boolean.class);
                                    boolean hasChestPain = dataSnapshot.child("Chest_pain").getValue(Boolean.class);
                                    boolean hypertension = dataSnapshot.child("hypertension").getValue(Boolean.class);
                                    boolean heartDisease = dataSnapshot.child("heart_disease").getValue(Boolean.class);
                                    double bmi = dataSnapshot.child("bmi").getValue(Double.class);
                                    double hba1cLevel = dataSnapshot.child("HbA1c_level").getValue(Double.class);
                                    double bloodGlucoseLevel = dataSnapshot.child("blood_glucose_level").getValue(Double.class);

                                    // Display the retrieved medical history
                                    displayMedicalRecord(
                                            age, gender, cp, trewstbps, chol, fbs, restecg, thalach, exang, oldpeak,
                                            slope, ca, thal, smoking, yellowFingers, anxiety, chronicDisease, fatigue,
                                            allergy, wheezing, swallowingDifficulty, hasChestPain, hypertension,
                                            heartDisease, bmi, hba1cLevel, bloodGlucoseLevel
                                    );
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
                                    String doctor = dataSnapshot.getValue(String.class);
                                    // Append the doctor information
                                    infoTextView.append("\nDoctor: " + doctor);
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
                                    String insurance = dataSnapshot.getValue(String.class);
                                    // Append the insurance company information
                                    infoTextView.append("\nInsurance Company: " + insurance);
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


    private void displayMedicalRecord(
            int age, boolean gender, int cp, int trewstbps, int chol, int fbs,
            int restecg, int thalach, boolean exang, double oldpeak, int slope, int ca,
            int thal, boolean smoking, boolean yellowFingers, boolean anxiety,
            boolean chronicDisease, boolean fatigue, boolean allergy, boolean wheezing,
            boolean swallowingDifficulty, boolean hasChestPain, boolean hypertension,
            boolean heartDisease, double bmi, double hba1cLevel, double bloodGlucoseLevel) {

        // Display the medical record information
        infoTextView.setText("Medical History:\n");
        infoTextView.append("Age: " + age + "\n");
        infoTextView.append("Gender: " + (gender ? "Male" : "Female") + "\n");
        infoTextView.append("Chest Pain Type: " + cp + "\n");
        infoTextView.append("Resting Blood Pressure: " + trewstbps + "\n");
        infoTextView.append("Cholesterol: " + chol + "\n");
        infoTextView.append("Fasting Blood Sugar: " + fbs + "\n");
        infoTextView.append("Resting Electrocardiographic Results: " + restecg + "\n");
        infoTextView.append("Maximum Heart Rate Achieved: " + thalach + "\n");
        infoTextView.append("Exercise Induced Angina: " + (exang ? "Yes" : "No") + "\n");
        infoTextView.append("Oldpeak: " + oldpeak + "\n");
        infoTextView.append("Slope of the Peak Exercise ST Segment: " + slope + "\n");
        infoTextView.append("Number of Major Vessels Colored by Fluoroscopy: " + ca + "\n");
        infoTextView.append("Thal: " + thal + "\n");
        infoTextView.append("Smoking: " + (smoking ? "Yes" : "No") + "\n");
        infoTextView.append("Yellow Fingers: " + (yellowFingers ? "Yes" : "No") + "\n");
        infoTextView.append("Anxiety: " + (anxiety ? "Yes" : "No") + "\n");
        infoTextView.append("Chronic Disease: " + (chronicDisease ? "Yes" : "No") + "\n");
        infoTextView.append("Fatigue: " + (fatigue ? "Yes" : "No") + "\n");
        infoTextView.append("Allergy: " + (allergy ? "Yes" : "No") + "\n");
        infoTextView.append("Wheezing: " + (wheezing ? "Yes" : "No") + "\n");
        infoTextView.append("Swallowing Difficulty: " + (swallowingDifficulty ? "Yes" : "No") + "\n");
        infoTextView.append("Chest Pain: " + (hasChestPain ? "Yes" : "No") + "\n");
        infoTextView.append("Hypertension: " + (hypertension ? "Yes" : "No") + "\n");
        infoTextView.append("Heart Disease: " + (heartDisease ? "Yes" : "No") + "\n");
        infoTextView.append("BMI: " + bmi + "\n");
        infoTextView.append("HbA1c Level: " + hba1cLevel + "\n");
        infoTextView.append("Blood Glucose Level: " + bloodGlucoseLevel + "\n");
    }
}
