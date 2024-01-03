package com.example.LyfeRisk;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ContactActivity extends AppCompatActivity {

    private DatabaseReference mDatabase, mMedicalDatabase,mDoctorDatabase;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        // Makes the app in portrait mode only
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("PatientUsers");
        mMedicalDatabase = FirebaseDatabase.getInstance().getReference("MedicalRecords");
        mDoctorDatabase = FirebaseDatabase.getInstance().getReference("DoctorUsers");
    }

    public void emailDoctor(View view) {
        String currentUserId = mAuth.getCurrentUser().getUid();

        mDatabase.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String linkedDoctorIMC = dataSnapshot.child("linkedDoctorIMC").getValue(String.class);
                    Log.d("ContactActivity", "Linked Doctor IMC: " + linkedDoctorIMC);

                    mDoctorDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot doctorSnapshot) {

                            for (DataSnapshot doctorData : doctorSnapshot.getChildren()) {
                                String doctorIMC = doctorData.child("imc").getValue(String.class);

                                if (doctorIMC != null && doctorIMC.equals(linkedDoctorIMC)) {
                                    String docEmail = doctorData.child("email").getValue(String.class);
                                    makeEmail(docEmail);
                                    return;
                                }
                            }

                            Toast.makeText(ContactActivity.this, "Doctor not found", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(ContactActivity.this, "Error retrieving doctor data", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(ContactActivity.this, "Patient data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ContactActivity.this, "Error retrieving patient data", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void makeEmail(String docEmail){
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + docEmail));
        startActivity(emailIntent);
    }

    public void emailInsurance(View view) {
        String email = "insurance@example.com";
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
        emailIntent.setData(Uri.parse("mailto:" + email));
        startActivity(emailIntent);
    }

    public void callDoctor(View view) {
        String currentUserId = mAuth.getCurrentUser().getUid();

        mDatabase.child(currentUserId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String linkedDoctorIMC = dataSnapshot.child("linkedDoctorIMC").getValue(String.class);
                    Log.d("ContactActivity", "Linked Doctor IMC: " + linkedDoctorIMC);

                    mDoctorDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot doctorSnapshot) {

                            for (DataSnapshot doctorData : doctorSnapshot.getChildren()) {
                                String doctorIMC = doctorData.child("imc").getValue(String.class);

                                if (doctorIMC != null && doctorIMC.equals(linkedDoctorIMC)) {
                                    String telNum = doctorData.child("phone").getValue(String.class);
                                    makeCall(telNum);
                                    return;
                                }
                            }

                            Toast.makeText(ContactActivity.this, "Doctor not found", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(ContactActivity.this, "Error retrieving doctor data", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(ContactActivity.this, "Patient data not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ContactActivity.this, "Error retrieving patient data", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void callInsurance(View view) {
        // Retrieve insurance phone number from the database
        if (mAuth.getCurrentUser() != null) {
            String userId = mAuth.getCurrentUser().getUid();
            mDatabase.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String insuranceTelNum = dataSnapshot.child("insuranceTelNum").getValue(String.class);
                        makeCall(insuranceTelNum);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Handle the error if any
                    Toast.makeText(ContactActivity.this, "Error retrieving insurance phone number", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void makeCall(String phoneNum) {
        if (phoneNum != null && !phoneNum.isEmpty()) {
            Intent callIntent = new Intent(Intent.ACTION_DIAL);
            callIntent.setData(Uri.parse("tel:" + phoneNum));
            startActivity(callIntent);
        } else {
            Toast.makeText(ContactActivity.this, "Phone number not available", Toast.LENGTH_SHORT).show();
        }
    }

    public void callHospital(View view) {
        String phoneNumber = "5555555555";
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }

    public void callAE(View view) {
        String phoneNumber = "9999999999";
        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(callIntent);
    }
}
