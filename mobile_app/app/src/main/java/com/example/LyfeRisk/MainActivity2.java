package com.example.LyfeRisk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        //makes nav bar invisible
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        //step 1 find the view
        Button btn1 = findViewById(R.id.loginBtn);
        TabLayout tl = findViewById(R.id.tabLayout);
        //step 2 set on click method
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //step 3 create on intent
                Intent intent1 = new Intent(MainActivity2.this, MainActivity.class);
                //step3 start activity
                startActivity(intent1);
            }
        });

        }

    public void goHome(View view) {
        Intent intent2 = new Intent(MainActivity2.this, MainActivity.class);
        try {
            startActivity(intent2);
        } catch (ActivityNotFoundException e) {
            // toast message no app found ti open a dialer

        }
    }

    public void goToRegister(View view) {
        Intent intent3 = new Intent(MainActivity2.this, MainActivity3.class);
        try {
            startActivity(intent3);
        } catch (ActivityNotFoundException e) {
            // toast message no app found ti open a dialer

        }
    }

    public void goToLogin(View view) {
        Intent intent4 = new Intent(MainActivity2.this, MainActivity2.class);
        try {
            startActivity(intent4);
        } catch (ActivityNotFoundException e) {
            // toast message no app found ti open a dialer

        }
    }

}