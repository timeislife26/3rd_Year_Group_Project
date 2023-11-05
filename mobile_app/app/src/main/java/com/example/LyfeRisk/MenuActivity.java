package com.example.LyfeRisk;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;

public class MenuActivity extends AppCompatActivity {

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        // Makes the app in portrait mode only
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    public void goToSettings(View view) {
        Intent intent = new Intent(MenuActivity.this, SettingsActivity.class);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {

        }
    }
    public void goToReview(View view) {
        String appPackageName = "com.example.LyfeRisk";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName));
        startActivity(intent); // this would work if our app was on google play
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