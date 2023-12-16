package com.example.LyfeRisk;

import androidx.appcompat.app.AppCompatActivity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
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

    private EditText editAge, editTrewstbps, editChol, editThalach, editOldpeak,
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
        spinnerYellowFingers.setAdapter(yesnoAdapter);
        spinnerAnxiety.setAdapter(yesnoAdapter);
        spinnerChronicDisease.setAdapter(yesnoAdapter);
        spinnerFatigue.setAdapter(yesnoAdapter);
        spinnerWheezing.setAdapter(yesnoAdapter);
        spinnerSwallowingDifficulty.setAdapter(yesnoAdapter);
        spinnerHypertension.setAdapter(yesnoAdapter);
        spinnerHeartDisease.setAdapter(yesnoAdapter);
        spinnerAllergy.setAdapter(yesnoAdapter);

        ArrayAdapter<CharSequence> smokingAdapter = ArrayAdapter.createFromResource(this, R.array.smoking, android.R.layout.simple_spinner_item);
        smokingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSmoking.setAdapter(smokingAdapter);


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

    private boolean mapSmoking(String selectedItem) {
        return selectedItem.equals("Currently");
    }

    public void saveAndQuit(View view) {
        int cp = mapChestPain(spinnerChestPain.getSelectedItem().toString());
        boolean exang = checkBoxExang.isChecked();
        boolean anxiety = mapYesNo(spinnerAnxiety.getSelectedItem().toString());
        boolean chronicDisease = mapYesNo(spinnerChronicDisease.getSelectedItem().toString());
        boolean fatigue = mapYesNo(spinnerFatigue.getSelectedItem().toString());
        boolean allergy = mapYesNo(spinnerAllergy.getSelectedItem().toString());
        boolean wheezing = mapYesNo(spinnerWheezing.getSelectedItem().toString());
        boolean swallowingDifficulty = mapYesNo(spinnerSwallowingDifficulty.getSelectedItem().toString());
        boolean hasChestPain = checkBoxChestPain.isChecked();
        boolean hypertension = mapYesNo(spinnerHypertension.getSelectedItem().toString());
        boolean heartDisease = mapYesNo(spinnerHeartDisease.getSelectedItem().toString());

        if (TextUtils.isEmpty(editAge.getText().toString())
                || TextUtils.isEmpty(editTrewstbps.getText().toString())
                || TextUtils.isEmpty(editChol.getText().toString())
                || TextUtils.isEmpty(editFbs.getText().toString())
                || TextUtils.isEmpty(editThalach.getText().toString())
                || TextUtils.isEmpty(editOldpeak.getText().toString())
                || TextUtils.isEmpty(editBMI.getText().toString())
                || TextUtils.isEmpty(editHbA1cLevel.getText().toString())
                || TextUtils.isEmpty(editBloodGlucoseLevel.getText().toString())) {
            Toast.makeText(this, "Please fill in all the required fields", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            int age = Integer.parseInt(this.editAge.getText().toString());
            if (age < 1 || age > 110) {
                Toast.makeText(this, "Please enter a valid age", Toast.LENGTH_SHORT).show();
                return;
            }

            boolean gender = checkBoxMale.isChecked();

            int trewstbps = Integer.parseInt(this.editTrewstbps.getText().toString());
            if (trewstbps < 70 || trewstbps > 200) {
                Toast.makeText(this, "Please enter a valid resting blood pressure", Toast.LENGTH_SHORT).show();
                return;
            }


            int chol = Integer.parseInt(this.editChol.getText().toString());
            if (chol < 70 || chol > 300) {
                Toast.makeText(this, "Please enter a valid cholesterol", Toast.LENGTH_SHORT).show();
                return;
            }

            int fbs = Integer.parseInt(this.editFbs.getText().toString());
            if (fbs < 60 || fbs > 150) {
                Toast.makeText(this, "Please enter a valid blood sugar", Toast.LENGTH_SHORT).show();
                return;
            }

            int restecg = mapRestecg(spinnerRestecg.getSelectedItem().toString());

            int maxHeartRate = 208 - (int) (0.7 * age);
            int thalach = Integer.parseInt(this.editThalach.getText().toString());
            if (thalach < 70 || thalach > maxHeartRate) {
                Toast.makeText(this, "Please enter a valid maximum heart rate", Toast.LENGTH_SHORT).show();
                return;
            }


            float oldpeak = Float.parseFloat(this.editOldpeak.getText().toString());
            if (oldpeak < -2.5 || oldpeak > 2.5) {
                Toast.makeText(this, "Please enter a valid ST Depression value", Toast.LENGTH_SHORT).show();
                return;
            }

            int slope = mapSlope(spinnerSlope.getSelectedItem().toString());
            int ca = mapCa(spinnerCa.getSelectedItem().toString());
            int thal = mapThal(spinnerThal.getSelectedItem().toString());

            boolean smoking = mapSmoking(spinnerSmoking.getSelectedItem().toString());

            boolean yellowFingers = mapYesNo(spinnerYellowFingers.getSelectedItem().toString());
            float bmi = Float.parseFloat(this.editBMI.getText().toString());
            if (bmi < 10 || bmi > 50) {
                Toast.makeText(this, "Please enter a valid BMI", Toast.LENGTH_SHORT).show();
                return;
            }


            float hba1cLevel = Float.parseFloat(this.editHbA1cLevel.getText().toString());
            if (hba1cLevel < 4 || hba1cLevel > 12) {
                Toast.makeText(this, "Please enter a valid HbA1c level", Toast.LENGTH_SHORT).show();
                return;
            }


            int bloodGlucoseLevel = Integer.parseInt(this.editBloodGlucoseLevel.getText().toString());
            if (bloodGlucoseLevel < 70 || bloodGlucoseLevel > 210) {
                Toast.makeText(this, "Please enter a valid blood glucose level", Toast.LENGTH_SHORT).show();
                return;
            }

            String userUid = mAuth.getCurrentUser().getUid();
            mDatabase.child(userUid).child("Age").setValue(age);
            mDatabase.child(userUid).child("gender").setValue(gender);
            mDatabase.child(userUid).child("cp").setValue(cp);
            mDatabase.child(userUid).child("trewstbps").setValue(trewstbps);
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
            mDatabase.child(userUid).child("Yellow_Fingers").setValue(yellowFingers);
            mDatabase.child(userUid).child("Anxiety").setValue(anxiety);
            mDatabase.child(userUid).child("Chronic_Disease").setValue(chronicDisease);
            mDatabase.child(userUid).child("Fatigue").setValue(fatigue);
            mDatabase.child(userUid).child("Allergy").setValue(allergy);
            mDatabase.child(userUid).child("Wheezing").setValue(wheezing);
            mDatabase.child(userUid).child("Swallowing_Difficulty").setValue(swallowingDifficulty);
            mDatabase.child(userUid).child("Chest_pain").setValue(hasChestPain);
            mDatabase.child(userUid).child("hypertension").setValue(hypertension);
            mDatabase.child(userUid).child("heart_disease").setValue(heartDisease);
            mDatabase.child(userUid).child("bmi").setValue(bmi);
            mDatabase.child(userUid).child("HbA1c_level").setValue(hba1cLevel);
            mDatabase.child(userUid).child("blood_glucose_level").setValue(bloodGlucoseLevel);
            mDatabase.child(userUid).child("userId").setValue(mAuth.getCurrentUser().getUid());


            Toast.makeText(this, "Medical records saved", Toast.LENGTH_SHORT).show();
            finish();
        } catch (NumberFormatException ignored) {
        }
    }
}
