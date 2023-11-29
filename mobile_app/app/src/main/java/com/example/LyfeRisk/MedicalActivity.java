package com.example.LyfeRisk;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MedicalActivity extends AppCompatActivity {

    private EditText medicalNotes, editAge, editTrewstbps, editChol, editThalach, editOldpeak,
            editFbs, editBMI, editHbA1cLevel, editBloodGlucoseLevel;
    private CheckBox checkBoxChestPain, checkBoxMale, checkBoxFemale, checkBoxExang;
    private Spinner spinnerChestPain, spinnerRestecg, spinnerSlope, spinnerCa, spinnerThal,
            spinnerSmoking, spinnerYellowFingers, spinnerAnxiety, spinnerChronicDisease, spinnerFatigue,
            spinnerAllergy, spinnerWheezing, spinnerSwallowingDifficulty, spinnerHypertension, spinnerHeartDisease;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("MedicalRecords");

        medicalNotes = findViewById(R.id.editTextTextMultiLine2);
        editAge = findViewById(R.id.editAge);
        editTrewstbps = findViewById(R.id.editTrewstbps);
        editChol = findViewById(R.id.editChol);
        editThalach = findViewById(R.id.editThalach);
        editOldpeak = findViewById(R.id.editOldpeak);
        editFbs = findViewById(R.id.editFbs);
        editBMI = findViewById(R.id.editBMI);
        editHbA1cLevel = findViewById(R.id.editHbA1cLevel);
        editBloodGlucoseLevel = findViewById(R.id.editBloodGlucoseLevel);

        checkBoxChestPain = findViewById(R.id.checkBoxChestPain);
        checkBoxMale = findViewById(R.id.checkBoxMale);
        checkBoxFemale = findViewById(R.id.checkBoxFemale);
        checkBoxExang = findViewById(R.id.checkBoxExang);

        spinnerChestPain = findViewById(R.id.spinnerChestPain);
        spinnerRestecg = findViewById(R.id.spinnerRestecg);
        spinnerSlope = findViewById(R.id.spinnerSlope);
        spinnerCa = findViewById(R.id.spinnerCa);
        spinnerThal = findViewById(R.id.spinnerThal);
        spinnerSmoking = findViewById(R.id.spinnerSmoking);
        spinnerYellowFingers = findViewById(R.id.spinnerYellowFingers);
        spinnerAnxiety = findViewById(R.id.spinnerAnxiety);
        spinnerChronicDisease = findViewById(R.id.spinnerChronicDisease);
        spinnerFatigue = findViewById(R.id.spinnerFatigue);
        spinnerAllergy = findViewById(R.id.spinnerAllergy);
        spinnerWheezing = findViewById(R.id.spinnerWheezing);
        spinnerSwallowingDifficulty = findViewById(R.id.spinnerSwallowingDifficulty);
        spinnerHypertension = findViewById(R.id.spinnerHypertension);
        spinnerHeartDisease = findViewById(R.id.spinnerHeartDisease);

        ArrayAdapter<CharSequence> cpAdapter = ArrayAdapter.createFromResource(this, R.array.chest_pain, android.R.layout.simple_spinner_item);
        cpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChestPain.setAdapter(cpAdapter);

        ArrayAdapter<CharSequence> restecgAdapter = ArrayAdapter.createFromResource(this, R.array.restecg, android.R.layout.simple_spinner_item);
        restecgAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRestecg.setAdapter(restecgAdapter);

        ArrayAdapter<CharSequence> slopeAdapter = ArrayAdapter.createFromResource(this, R.array.slope, android.R.layout.simple_spinner_item);
        slopeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSlope.setAdapter(slopeAdapter);

        ArrayAdapter<CharSequence> caAdapter = ArrayAdapter.createFromResource(this, R.array.ca, android.R.layout.simple_spinner_item);
        caAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCa.setAdapter(caAdapter);

        ArrayAdapter<CharSequence> thalAdapter = ArrayAdapter.createFromResource(this, R.array.thal, android.R.layout.simple_spinner_item);
        thalAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerThal.setAdapter(thalAdapter);

        ArrayAdapter<CharSequence> yesnoAdapter = ArrayAdapter.createFromResource(this, R.array.yesno, android.R.layout.simple_spinner_item);
        yesnoAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSmoking.setAdapter(yesnoAdapter);
        spinnerYellowFingers.setAdapter(yesnoAdapter);
        spinnerAnxiety.setAdapter(yesnoAdapter);
        spinnerChronicDisease.setAdapter(yesnoAdapter);
        spinnerFatigue.setAdapter(yesnoAdapter);
        spinnerWheezing.setAdapter(yesnoAdapter);
        spinnerSwallowingDifficulty.setAdapter(yesnoAdapter);
        spinnerHypertension.setAdapter(yesnoAdapter);
        spinnerHeartDisease.setAdapter(yesnoAdapter);
        spinnerAllergy.setAdapter(yesnoAdapter);

        checkBoxChestPain.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                spinnerChestPain.setVisibility(View.VISIBLE);
            } else {
                spinnerChestPain.setVisibility(View.GONE);
            }
        });

        checkBoxMale.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkBoxFemale.setChecked(false);
            }
        });

        checkBoxFemale.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                checkBoxMale.setChecked(false);
            }
        });

        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(this::saveAndQuit);
    }

    private int mapSpinnerSelection(String selectedItem, String[] values) {
        for (int i = 0; i < values.length; i++) {
            if (values[i].equals(selectedItem)) {
                return i;
            }
        }
        return -1;
    }

    private int mapChestPain(String selectedItem) {
        String[] values = getResources().getStringArray(R.array.chest_pain);
        return mapSpinnerSelection(selectedItem, values);
    }

    private int mapRestecg(String selectedItem) {
        String[] values = getResources().getStringArray(R.array.restecg);
        return mapSpinnerSelection(selectedItem, values);
    }

    private int mapSlope(String selectedItem) {
        String[] values = getResources().getStringArray(R.array.slope);
        return mapSpinnerSelection(selectedItem, values);
    }

    private int mapCa(String selectedItem) {
        String[] values = getResources().getStringArray(R.array.ca);
        return mapSpinnerSelection(selectedItem, values);
    }

    private int mapThal(String selectedItem) {
        String[] values = getResources().getStringArray(R.array.thal);
        return mapSpinnerSelection(selectedItem, values);
    }

    private boolean mapYesNo(String selectedItem) {
        String[] values = getResources().getStringArray(R.array.yesno);
        return "Yes".equals(selectedItem);
    }

    public void saveAndQuit(View view) {
        String medicalNotes = this.medicalNotes.getText().toString();
        int age = Integer.parseInt(this.editAge.getText().toString());
        boolean gender = checkBoxMale.isChecked();
        int trewstbps = Integer.parseInt(this.editTrewstbps.getText().toString());
        int cp = mapChestPain(spinnerChestPain.getSelectedItem().toString());
        int chol = Integer.parseInt(this.editChol.getText().toString());
        int fbs = Integer.parseInt(this.editFbs.getText().toString());
        int restecg = mapRestecg(spinnerRestecg.getSelectedItem().toString());
        int thalach = Integer.parseInt(this.editThalach.getText().toString());
        boolean exang = checkBoxExang.isChecked();
        float oldpeak = Float.parseFloat(this.editOldpeak.getText().toString());
        int slope = mapSlope(spinnerSlope.getSelectedItem().toString());
        int ca = mapCa(spinnerCa.getSelectedItem().toString());
        int thal = mapThal(spinnerThal.getSelectedItem().toString());
        boolean smoking = mapYesNo(spinnerSmoking.getSelectedItem().toString());
        boolean yellowFingers = mapYesNo(spinnerYellowFingers.getSelectedItem().toString());
        boolean anxiety = mapYesNo(spinnerAnxiety.getSelectedItem().toString());
        boolean chronicDisease = mapYesNo(spinnerChronicDisease.getSelectedItem().toString());
        boolean fatigue = mapYesNo(spinnerFatigue.getSelectedItem().toString());
        boolean allergy = mapYesNo(spinnerAllergy.getSelectedItem().toString());
        boolean wheezing = mapYesNo(spinnerWheezing.getSelectedItem().toString());
        boolean swallowingDifficulty = mapYesNo(spinnerSwallowingDifficulty.getSelectedItem().toString());
        boolean hasChestPain = checkBoxChestPain.isChecked();
        boolean hypertension = mapYesNo(spinnerHypertension.getSelectedItem().toString());
        boolean heartDisease = mapYesNo(spinnerHeartDisease.getSelectedItem().toString());
        float bmi = Float.parseFloat(this.editBMI.getText().toString());
        float hba1cLevel = Float.parseFloat(this.editHbA1cLevel.getText().toString());
        int bloodGlucoseLevel = Integer.parseInt(this.editBloodGlucoseLevel.getText().toString());

        String userUid = mAuth.getCurrentUser().getUid();
        mDatabase.child(userUid).child("notes").setValue(medicalNotes);
        mDatabase.child(userUid).child("Age").setValue(age);
        mDatabase.child(userUid).child("gender").setValue(gender);
        mDatabase.child(userUid).child("cp").setValue(cp);
        mDatabase.child(userUid).child("trewstbps ").setValue(trewstbps);
        mDatabase.child(userUid).child("chol").setValue(chol);
        mDatabase.child(userUid).child("fbs").setValue(fbs);
        mDatabase.child(userUid).child("restecg").setValue(restecg);
        mDatabase.child(userUid).child("thalach").setValue(thalach);
        mDatabase.child(userUid).child("exang").setValue(exang);
        mDatabase.child(userUid).child("oldpeak").setValue(oldpeak);
        mDatabase.child(userUid).child("slope").setValue(slope);
        mDatabase.child(userUid).child("ca").setValue(ca);
        mDatabase.child(userUid).child("thal").setValue(thal);
        mDatabase.child(userUid).child("Smoking").setValue(smoking);
        mDatabase.child(userUid).child("Yellow_Fingers ").setValue(yellowFingers);
        mDatabase.child(userUid).child("Anxiety").setValue(anxiety);
        mDatabase.child(userUid).child("Chronic_Disease ").setValue(chronicDisease);
        mDatabase.child(userUid).child("Fatigue ").setValue(fatigue);
        mDatabase.child(userUid).child("Allergy").setValue(allergy);
        mDatabase.child(userUid).child("Wheezing").setValue(wheezing);
        mDatabase.child(userUid).child("Swallowing_Difficulty ").setValue(swallowingDifficulty);
        mDatabase.child(userUid).child("Chest_pain ").setValue(hasChestPain);
        mDatabase.child(userUid).child("hypertension").setValue(hypertension);
        mDatabase.child(userUid).child("heart_disease").setValue(heartDisease);
        mDatabase.child(userUid).child("bmi").setValue(bmi);
        mDatabase.child(userUid).child("HbA1c_level").setValue(hba1cLevel);
        mDatabase.child(userUid).child("blood_glucose_level ").setValue(bloodGlucoseLevel);
        mDatabase.child(userUid).child("userId ").setValue(mAuth.getCurrentUser().getUid());


        Toast.makeText(this, "Medical records saved", Toast.LENGTH_SHORT).show();
        finish();
    }
}
