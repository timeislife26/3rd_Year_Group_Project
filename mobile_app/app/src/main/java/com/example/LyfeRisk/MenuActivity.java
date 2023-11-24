package com.example.LyfeRisk;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MenuActivity extends AppCompatActivity {

    private boolean hasPaid = false; // Flag to track if the user has paid
    private InterstitialAd interstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        MobileAds.initialize(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Check if the user has paid
        checkPaymentStatus();
    }

    private void checkPaymentStatus() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("PatientUsers").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    hasPaid = snapshot.child("isPaid").getValue(Boolean.class);
                    if (!hasPaid) {
                        // User has not paid, and coming from the login page, show the payment popup
                        showPaymentPopup();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void showPaymentPopup() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_subscription);
        dialog.setCancelable(false);

        // Disable the ability to dismiss the dialog by tapping outside of it
        dialog.setCanceledOnTouchOutside(false);


        Button payButton = dialog.findViewById(R.id.payButton);
        Button freeButton = dialog.findViewById(R.id.freeButton);

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Get the current user's ID
                String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

                // Reference to the user's database node
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("PatientUsers").child(userId);

                // Set the 'isPaid' field to true
                userRef.child("isPaid").setValue(true)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MenuActivity.this, "Payment successful. Thank you!", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MenuActivity.this, "Failed to update payment status. Please try again.", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        });
            }
        });

        freeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Load and display the interstitial ad only when the continue button is pressed
                loadAndDisplayInterstitialAd(dialog);
            }
        });

        dialog.show();
    }

    private void loadAndDisplayInterstitialAd(final Dialog dialog) {
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/8691691433", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd ad) {
                        // The interstitialAd reference will be null until an ad is loaded.
                        interstitialAd = ad;
                        if (interstitialAd != null) {
                            interstitialAd.show(MenuActivity.this);
                            interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                                @Override
                                public void onAdDismissedFullScreenContent() {
                                    continueWithFreeVersion(dialog);
                                }

                                @Override
                                public void onAdFailedToShowFullScreenContent(AdError adError) {
                                    continueWithFreeVersion(dialog);
                                }
                            });
                        } else {
                            continueWithFreeVersion(dialog);
                        }
                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        continueWithFreeVersion(dialog);
                    }
                });
    }


    private void continueWithFreeVersion(Dialog dialog) {
        // User chooses to continue with the free version
        dialog.dismiss();
    }

    public void manualPaymentPopup(View view){
        showPaymentPopup();
    }

    public void goToSettings(View view) {
        Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {

        }
    }
    public void goToReview(View view) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_review);
        dialog.setCancelable(true);

        final RatingBar ratingBar = dialog.findViewById(R.id.ratingBar);
        final EditText editTextReview = dialog.findViewById(R.id.editTextReview); // Reference to the EditText

        // Listener for rating changes.
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                if (fromUser) {
                    Toast.makeText(MenuActivity.this, "Selected Rating: " + rating, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button btnSubmit = dialog.findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float rating = ratingBar.getRating();
                String reviewText = editTextReview.getText().toString(); // Get the review text from the EditText
                Toast.makeText(MenuActivity.this, "Review submitted with rating: " + rating + " and text: " + reviewText, Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public void showTermsAndConditions(View view) {
        // Start the Terms and Conditions activity
        Intent intent = new Intent(MenuActivity.this, CreditsActivity.class);
        TextView titleTextView = findViewById(R.id.editTextText3);
        TextView contentTextView = findViewById(R.id.textView3);

        // Set the new title and text
        titleTextView.setText("Terms &amp; Conditions");
        contentTextView.setText("Lorem Ipsum");

        startActivity(intent);
    }


    public void goToCredits(View view) {
        Intent intent = new Intent(MenuActivity.this, CreditsActivity.class);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {

        }
    }
    public void goToChat(View view) {
        Intent intent = new Intent(MenuActivity.this, ChatActivity.class);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {

        }
    }
    public void goToMedical(View view) {
        Intent intent = new Intent(MenuActivity.this, MedicalActivity.class);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {

        }
    }

    public void goToRecords(View view) {
        Intent intent = new Intent(MenuActivity.this, RecordsActivity.class);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {

        }
    }

    public void goToContact(View view) {
        Intent intent = new Intent(MenuActivity.this, ContactActivity.class);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {

        }
    }
}