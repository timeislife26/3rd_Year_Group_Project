package com.example.LyfeRisk;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
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
                    String userid = mAuth.getCurrentUser().getUid();

                    if (mAuth.getCurrentUser() != null) {
                        try {
                            // Retrieve the patient's medical history from the database
                            mMedicalDatabase.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        // Retrieve all medical record fields directly
                                        try {
                                            Integer age = dataSnapshot.child("Age").getValue(Integer.class);
                                            Boolean gender = dataSnapshot.child("gender").getValue(Boolean.class);
                                            Integer cp = dataSnapshot.child("cp").getValue(Integer.class);
                                            Integer trewstbps = dataSnapshot.child("trewstbps").getValue(Integer.class);
                                            Integer chol = dataSnapshot.child("chol").getValue(Integer.class);
                                            Integer fbs = dataSnapshot.child("fbs").getValue(Integer.class);
                                            Integer restecg = dataSnapshot.child("restecg").getValue(Integer.class);
                                            Integer thalach = dataSnapshot.child("thalach").getValue(Integer.class);
                                            Boolean exang = dataSnapshot.child("exang").getValue(Boolean.class);
                                            Double oldpeak = dataSnapshot.child("oldpeak").getValue(Double.class);
                                            Integer slope = dataSnapshot.child("slope").getValue(Integer.class);
                                            Integer ca = dataSnapshot.child("ca").getValue(Integer.class);
                                            Integer thal = dataSnapshot.child("thal").getValue(Integer.class);
                                            Boolean smoking = dataSnapshot.child("Smoking").getValue(Boolean.class);
                                            Boolean yellowFingers = dataSnapshot.child("Yellow_Fingers").getValue(Boolean.class);
                                            Boolean anxiety = dataSnapshot.child("Anxiety").getValue(Boolean.class);
                                            Boolean chronicDisease = dataSnapshot.child("Chronic_Disease").getValue(Boolean.class);
                                            Boolean fatigue = dataSnapshot.child("Fatigue").getValue(Boolean.class);
                                            Boolean allergy = dataSnapshot.child("Allergy").getValue(Boolean.class);
                                            Boolean wheezing = dataSnapshot.child("Wheezing").getValue(Boolean.class);
                                            Boolean swallowingDifficulty = dataSnapshot.child("Swallowing_Difficulty").getValue(Boolean.class);
                                            Boolean hasChestPain = dataSnapshot.child("Chest_pain").getValue(Boolean.class);
                                            Boolean hypertension = dataSnapshot.child("hypertension").getValue(Boolean.class);
                                            Boolean heartDisease = dataSnapshot.child("heart_disease").getValue(Boolean.class);
                                            Double bmi = dataSnapshot.child("bmi").getValue(Double.class);
                                            Double hba1cLevel = dataSnapshot.child("HbA1c_level").getValue(Double.class);
                                            Double bloodGlucoseLevel = dataSnapshot.child("blood_glucose_level").getValue(Double.class);

                                            Log.d("FirebaseData", "Age: " + age);
                                            Log.d("FirebaseData", "Gender: " + gender);
                                            Log.d("FirebaseData", "Chest Pain Type: " + cp);
                                            Log.d("FirebaseData", "Resting Blood Pressure: " + trewstbps);
                                            Log.d("FirebaseData", "Cholesterol: " + chol);
                                            Log.d("FirebaseData", "Fasting Blood Sugar: " + fbs);
                                            Log.d("FirebaseData", "Resting Electrocardiographic Results: " + restecg);
                                            Log.d("FirebaseData", "Maximum Heart Rate Achieved: " + thalach);
                                            Log.d("FirebaseData", "Exercise Induced Angina: " + exang);
                                            Log.d("FirebaseData", "Oldpeak: " + oldpeak);
                                            Log.d("FirebaseData", "Slope of the Peak Exercise ST Segment: " + slope);
                                            Log.d("FirebaseData", "Number of Major Vessels Colored by Fluoroscopy: " + ca);
                                            Log.d("FirebaseData", "Thal: " + thal);
                                            Log.d("FirebaseData", "Smoking: " + smoking);
                                            Log.d("FirebaseData", "Yellow Fingers: " + yellowFingers);
                                            Log.d("FirebaseData", "Anxiety: " + anxiety);
                                            Log.d("FirebaseData", "Chronic Disease: " + chronicDisease);
                                            Log.d("FirebaseData", "Fatigue: " + fatigue);
                                            Log.d("FirebaseData", "Allergy: " + allergy);
                                            Log.d("FirebaseData", "Wheezing: " + wheezing);
                                            Log.d("FirebaseData", "Swallowing Difficulty: " + swallowingDifficulty);
                                            Log.d("FirebaseData", "Chest Pain: " + hasChestPain);
                                            Log.d("FirebaseData", "Hypertension: " + hypertension);
                                            Log.d("FirebaseData", "Heart Disease: " + heartDisease);
                                            Log.d("FirebaseData", "BMI: " + bmi);
                                            Log.d("FirebaseData", "HbA1c Level: " + hba1cLevel);
                                            Log.d("FirebaseData", "Blood Glucose Level: " + bloodGlucoseLevel);


                                            // Display the retrieved medical history
                                            displayMedicalRecord(
                                                    age, gender, cp, trewstbps, chol, fbs, restecg, thalach, exang, oldpeak,
                                                    slope, ca, thal, smoking, yellowFingers, anxiety, chronicDisease, fatigue,
                                                    allergy, wheezing, swallowingDifficulty, hasChestPain, hypertension,
                                                    heartDisease, bmi, hba1cLevel, bloodGlucoseLevel
                                            );
                                        } catch (NullPointerException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                }
                            });
                        } catch (NullPointerException e) {
                            e.printStackTrace();
                        }
                    }
                } else if ("Doctor".equals(selectedOption)) {
                    // Show the save button and populate spinnerDetails with "Doctor" options
                    infoTextView.setVisibility(View.GONE);
                    saveButton.setVisibility(View.VISIBLE);
                    detailsAdapter = ArrayAdapter.createFromResource(RecordsActivity.this, R.array.doctors , android.R.layout.simple_spinner_item);
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
