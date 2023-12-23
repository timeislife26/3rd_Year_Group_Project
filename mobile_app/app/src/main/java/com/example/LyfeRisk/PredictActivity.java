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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;


public class PredictActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase, mMedicalDatabase,mDoctorDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);
        Button predictBtn = findViewById(R.id.predictButton);
        TextView heartTv = findViewById(R.id.heartDiseaseTV);
        TextView lungTV = findViewById(R.id.LungCancerTV);
        TextView diabetesTV = findViewById(R.id.DiabetesTV);

        mAuth = FirebaseAuth.getInstance();
        mMedicalDatabase = FirebaseDatabase.getInstance().getReference("MedicalRecords");

        String userid = mAuth.getCurrentUser().getUid();
        predictBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heartTv.setText("Loading...");
                lungTV.setText("Loading...");
                diabetesTV.setText("Loading...");
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
                                        Boolean fbs = dataSnapshot.child("fbs").getValue(Boolean.class);
                                        Integer restecg = dataSnapshot.child("restecg").getValue(Integer.class);
                                        Integer thalach = dataSnapshot.child("thalach").getValue(Integer.class);
                                        Boolean exang = dataSnapshot.child("exang").getValue(Boolean.class);
                                        String oldpeak = dataSnapshot.child("oldpeak").getValue(String.class);
                                        Integer slope = dataSnapshot.child("slope").getValue(Integer.class);
                                        Integer ca = dataSnapshot.child("ca").getValue(Integer.class);
                                        Integer thal = dataSnapshot.child("thal").getValue(Integer.class);
                                        Integer smokingHistory = dataSnapshot.child("Smoking_history").getValue(Integer.class);
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
                                        Boolean alcholCon = dataSnapshot.child("Alcohol_Consuming").getValue(Boolean.class);
                                        Boolean coughing = dataSnapshot.child("Coughing").getValue(Boolean.class);
                                        Boolean sob = dataSnapshot.child("Shortness_of_Breath").getValue(Boolean.class);
                                        String bmi = dataSnapshot.child("bmi").getValue(String.class);
                                        String hba1cLevel = dataSnapshot.child("HbA1c_level").getValue(String.class);
                                        Integer bloodGlucoseLevel = dataSnapshot.child("blood_glucose_level").getValue(Integer.class);

                                        //Sending info to http request
                                        JSONObject heartData = new JSONObject();
                                        try {
                                            heartData.put("Age", age);
                                            heartData.put("ca", ca);
                                            heartData.put("chol", chol);
                                            heartData.put("cp", cp);
                                            heartData.put("exang", exang);
                                            heartData.put("fbs", fbs);
                                            heartData.put("oldpeak", oldpeak);
                                            heartData.put("restecg", restecg);
                                            heartData.put("gender", gender);
                                            heartData.put("slope", slope);
                                            heartData.put("thal", thal);
                                            heartData.put("thalach", thalach);
                                            heartData.put("trewstbps", trewstbps);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        String heartURL = "https://on-request-heart-ef42g3nnla-uc.a.run.app";
                                        RequestQueue queue = Volley.newRequestQueue(PredictActivity.this);

                                        StringRequest heartRequest = new StringRequest(Request.Method.POST, heartURL, response -> {
                                            Log.d("CloudFunctionResponse", response);
                                            heartTv.setText(response);
                                        }, error -> {
                                            // Handle error
                                            Log.e("CloudFunctionError", "Error sending data to Cloud Function: " + error.toString());
                                        }) {
                                            @Override
                                            public byte[] getBody() {
                                                return heartData.toString().getBytes();
                                            }

                                            @Override
                                            public String getBodyContentType() {
                                                return "application/json";
                                            }
                                        };

                                        JSONObject lungData = new JSONObject();
                                        try {
                                            lungData.put("gender", gender);
                                            lungData.put("Age", age);
                                            lungData.put("smoking", smoking);
                                            lungData.put("yf", yellowFingers);
                                            lungData.put("anxiety", anxiety);
                                            lungData.put("cd", chronicDisease);
                                            lungData.put("fatigue", fatigue);
                                            lungData.put("allergy", allergy);
                                            lungData.put("wheezing", wheezing);
                                            lungData.put("Alcohol", alcholCon);
                                            lungData.put("coughing", coughing);
                                            lungData.put("sob", sob);
                                            lungData.put("chest_pain", hasChestPain);
                                            lungData.put("sd", swallowingDifficulty);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        String lungUrl = "https://on-request-lung-ef42g3nnla-uc.a.run.app";
                                        //RequestQueue queue = Volley.newRequestQueue(PredictActivity.this);

                                        StringRequest lungRequest = new StringRequest(Request.Method.POST, lungUrl, response -> {
                                            Log.d("CloudFunctionResponse", response);
                                            lungTV.setText(response);
                                        }, error -> {
                                            // Handle error
                                            Log.e("CloudFunctionError", "Error sending data to Cloud Function: " + error.toString());
                                        }) {
                                            @Override
                                            public byte[] getBody() {
                                                return lungData.toString().getBytes();
                                            }

                                            @Override
                                            public String getBodyContentType() {
                                                return "application/json";
                                            }
                                        };

                                        JSONObject diabetesData = new JSONObject();
                                        try {
                                            diabetesData.put("gender", gender);
                                            diabetesData.put("Age", age);
                                            diabetesData.put("hypertension", hypertension);
                                            diabetesData.put("heart_disease", heartDisease);
                                            diabetesData.put("smoking_history", smokingHistory);
                                            diabetesData.put("bmi", bmi);
                                            diabetesData.put("hbA1c", hba1cLevel);
                                            diabetesData.put("blood_glucose_level", bloodGlucoseLevel);

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        String diabetesUrl = "https://on-request-diabetes-ef42g3nnla-uc.a.run.app";
                                        //RequestQueue queue = Volley.newRequestQueue(PredictActivity.this);

                                        StringRequest diabetesRequest = new StringRequest(Request.Method.POST, diabetesUrl, response -> {
                                            Log.d("CloudFunctionResponse", response);
                                            diabetesTV.setText(response);
                                        }, error -> {
                                            // Handle error
                                            Log.e("CloudFunctionError", "Error sending data to Cloud Function: " + error.toString());
                                        }) {
                                            @Override
                                            public byte[] getBody() {
                                                return diabetesData.toString().getBytes();
                                            }

                                            @Override
                                            public String getBodyContentType() {
                                                return "application/json";
                                            }
                                        };
                                        queue.add(heartRequest);
                                        queue.add(lungRequest);
                                        queue.add(diabetesRequest);
                                    }
                                    catch (Exception e){
                                        heartTv.setText("Sorry not enough data is available to make a prediction. Please fill out your medical records");
                                    }
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }catch (Exception e){
                        heartTv.setText("Error in connection to database");
                    }
                }
            }
        });
    }
}