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
import android.widget.EditText;
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

    private Spinner spinnerOptions;
    private TextView infoTextView, displayText, detailsTextView;
    private EditText imcEditText,insuranceEditText,policyNumEditText,phoneNoEditText;
    private Button saveButton,findButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase, mMedicalDatabase,mDoctorDatabase;
    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);
        // Makes the app in portrait mode only
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        detailsTextView = findViewById(R.id.detailsTextView);
        displayText = findViewById(R.id.textView6);
        spinnerOptions = findViewById(R.id.spinnerOptions);
        imcEditText = findViewById(R.id.imcEditText);
        infoTextView = findViewById(R.id.infoTextView);
        saveButton = findViewById(R.id.saveButton);
        findButton = findViewById(R.id.findButton);
        insuranceEditText = findViewById(R.id.insuranceEditText);
        policyNumEditText = findViewById(R.id.policyNumEditText);
        phoneNoEditText = findViewById(R.id.phoneNoEditText);


        infoTextView.setMovementMethod(new ScrollingMovementMethod());
        detailsTextView.setMovementMethod(new ScrollingMovementMethod());


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("PatientUsers");
        mMedicalDatabase = FirebaseDatabase.getInstance().getReference("MedicalRecords");
        mDoctorDatabase = FirebaseDatabase.getInstance().getReference("DoctorUsers");

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerOptions.setAdapter(adapter);

        spinnerOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String selectedOption = spinnerOptions.getSelectedItem().toString();

                // Check if "Patient Info" is selected
                if ("Patient Info".equals(selectedOption)) {
                    displayText.setText("Press the dropdown to get your information, choose your doctor or your Insurance company ");
                    infoTextView.setVisibility(View.VISIBLE);
                    saveButton.setVisibility(View.GONE);
                    imcEditText.setVisibility(View.GONE);
                    detailsTextView.setVisibility(View.GONE);
                    findButton.setVisibility(View.GONE);
                    insuranceEditText.setVisibility(View.GONE);
                    policyNumEditText.setVisibility(View.GONE);
                    phoneNoEditText.setVisibility(View.GONE);

                    String userid = mAuth.getCurrentUser().getUid();

                    if (mAuth.getCurrentUser() != null) {
                        try {
                            mMedicalDatabase.child(userid).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
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
                                            String oldpeak = dataSnapshot.child("oldpeak").getValue(String.class);
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
                                            String bmi = dataSnapshot.child("bmi").getValue(String.class);
                                            String hba1cLevel = dataSnapshot.child("HbA1c_level").getValue(String.class);
                                            Integer bloodGlucoseLevel = dataSnapshot.child("blood_glucose_level").getValue(Integer.class);

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
                                            displayRecords(
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
                    displayText.setText("Please enter the Doctor's IMC");
                    infoTextView.setVisibility(View.GONE);
                    saveButton.setVisibility(View.VISIBLE);
                    imcEditText.setVisibility(View.VISIBLE);
                    findButton.setVisibility(View.VISIBLE);
                    detailsTextView.setVisibility(View.VISIBLE);
                    displayText.setVisibility(View.VISIBLE);
                    insuranceEditText.setVisibility(View.GONE);
                    policyNumEditText.setVisibility(View.GONE);
                    phoneNoEditText.setVisibility(View.GONE);
                    detailsTextView.setText("");
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot userSnapshot) {
                            String userId = mAuth.getCurrentUser().getUid();
                            String linkedDoctorIMC = userSnapshot.child(userId).child("linkedDoctorIMC").getValue(String.class);

                            if (linkedDoctorIMC != null && !linkedDoctorIMC.isEmpty()) {
                                mDoctorDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot doctorSnapshot) {
                                        for (DataSnapshot doctorData : doctorSnapshot.getChildren()) {
                                            String doctorName = doctorData.child("fullName").getValue(String.class);
                                            String doctorIMC = doctorData.child("imc").getValue(String.class);

                                            if (doctorIMC != null && doctorIMC.equals(linkedDoctorIMC)) {
                                                detailsTextView.setText("Name: " + doctorName + "\n");
                                                detailsTextView.append("IMC: " + doctorIMC + "\n");
                                                return;
                                            }
                                        }

                                        detailsTextView.setText("No Doctor information found.");
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        detailsTextView.setText("Error retrieving data");
                                    }
                                });
                            } else {
                                detailsTextView.setText("No Doctor information found.");
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            detailsTextView.setText("Error retrieving user data");
                        }
                    });

                } else if ("Insurance".equals(selectedOption)) {
                    displayText.setText("Please enter your Insurance's Info");
                    infoTextView.setVisibility(View.GONE);
                    imcEditText.setVisibility(View.GONE);
                    findButton.setVisibility(View.GONE);
                    detailsTextView.setVisibility(View.VISIBLE);
                    saveButton.setVisibility(View.VISIBLE);
                    insuranceEditText.setVisibility(View.VISIBLE);
                    policyNumEditText.setVisibility(View.VISIBLE);
                    phoneNoEditText.setVisibility(View.VISIBLE);
                    detailsTextView.setText("");

                    if (mAuth.getCurrentUser() != null) {
                        String userUid = mAuth.getCurrentUser().getUid();
                        mDatabase.child(userUid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    String insuranceName = dataSnapshot.child("insuranceName").getValue(String.class);
                                    String insuranceTelNum = dataSnapshot.child("insuranceTelNum").getValue(String.class);
                                    int insurancePolicyNum = dataSnapshot.child("insurancePolicyNum").getValue(Integer.class);

                                    if (insuranceName != null) {
                                        // Display the current insurance information in detailsTextView
                                        detailsTextView.setText("Name: " + insuranceName + "\n");
                                        detailsTextView.append("Phone Number: " + insuranceTelNum + "\n");
                                        detailsTextView.append("Policy Number: " + insurancePolicyNum + "\n");

                                    } else {
                                        detailsTextView.setText("No insurance information found.");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                detailsTextView.setText("Error retrieving insurance data");
                            }
                        });
                    }
                } else {
                    infoTextView.setVisibility(View.GONE);
                    findButton.setVisibility(View.GONE);
                    saveButton.setVisibility(View.GONE);
                    imcEditText.setVisibility(View.GONE);
                    detailsTextView.setVisibility(View.GONE);
                    insuranceEditText.setVisibility(View.GONE);
                    policyNumEditText.setVisibility(View.GONE);
                    phoneNoEditText.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

        findButton.setOnClickListener(view -> {
            String imc = imcEditText.getText().toString().trim();
            if (!imc.isEmpty()) {
                mDoctorDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean found = false;
                        for (DataSnapshot doctorSnapshot : dataSnapshot.getChildren()) {
                            String doctorImc = doctorSnapshot.child("imc").getValue(String.class);
                            if (imc.equals(doctorImc)) {
                                // If a doctor with the provided IMC is found
                                String doctorName = doctorSnapshot.child("fullName").getValue(String.class);

                                detailsTextView.setText("Doctor's Name: " + doctorName);
                                infoTextView.setText("Doctor's Name: " + doctorName + "\n");
                                infoTextView.append("Doctor's IMC: " + doctorImc + "\n");

                                found = true;
                                break;

                            }
                        }

                        if (!found) {
                            // If no doctor found with the provided IMC
                            detailsTextView.setText("No doctors with IMC: " + imc);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        detailsTextView.setText("Error retrieving data");
                    }
                });
            } else {
            }
        });

        saveButton.setOnClickListener(view -> {
            String selectedOption = spinnerOptions.getSelectedItem().toString();

            if ("Doctor".equals(selectedOption)) {
                String doctorIMC = imcEditText.getText().toString().trim();

                if (!doctorIMC.isEmpty()) {
                    // Get the currently logged-in user's UID
                    String userUid = mAuth.getCurrentUser().getUid();

                    // Save the doctor's IMC to linkedDoctorIMC
                    mDatabase.child(userUid).child("linkedDoctorIMC").setValue(doctorIMC);

                    Toast.makeText(this, "Linked Doctor's IMC saved: " + doctorIMC, Toast.LENGTH_SHORT).show();
                } else {
                    detailsTextView.setText("Please enter the Doctor's IMC");
                }
            } else if ("Insurance".equals(selectedOption)) {
                String insuranceName = insuranceEditText.getText().toString().trim();
                String insuranceTelNum = phoneNoEditText.getText().toString().trim();
                int insurancePolicyNum = Integer.parseInt(policyNumEditText.getText().toString().trim());

                if (!insuranceName.isEmpty() && !(insurancePolicyNum == 0) && !insuranceTelNum.isEmpty()) {
                    // Get the currently logged-in user's UID
                    String userUid = mAuth.getCurrentUser().getUid();

                    // Save the doctor's IMC to linkedDoctorIMC
                    mDatabase.child(userUid).child("insuranceName").setValue(insuranceName);
                    mDatabase.child(userUid).child("insuranceTelNum").setValue(insuranceTelNum);
                    mDatabase.child(userUid).child("insurancePolicyNum").setValue(insurancePolicyNum);


                    Toast.makeText(this, "Insurance saved: " + insuranceName, Toast.LENGTH_SHORT).show();
                } else {
                    detailsTextView.setText("Please fill all the inputs");
                }
            } else {

                if (mAuth.getCurrentUser() != null) {
                    String message = "Data saved for " + selectedOption;

                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void displayRecords(
            int age, boolean gender, int cp, int trewstbps, int chol, int fbs,
            int restecg, int thalach, boolean exang, String oldpeak, int slope, int ca,
            int thal, boolean smoking, boolean yellowFingers, boolean anxiety,
            boolean chronicDisease, boolean fatigue, boolean allergy, boolean wheezing,
            boolean swallowingDifficulty, boolean hasChestPain, boolean hypertension,
            boolean heartDisease, String bmi, String hba1cLevel, int bloodGlucoseLevel) {

        // Display the medical record information
        infoTextView.append("Medical History:\n");
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