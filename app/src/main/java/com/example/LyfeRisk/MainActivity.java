package com.example.LyfeRisk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast.makeText(MainActivity.this,"on create method called Activity1",Toast.LENGTH_SHORT).show();



        //step 1 find the view
        Button loginBtn = findViewById(R.id.loginBtn);
        //step 2 set on click method
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //step 3 create on intent
                Intent intent1 = new Intent(MainActivity.this,MainActivity2.class);
                //step3 start activity
                startActivity(intent1);
            }
        });

        //step 1 find the view
        Button registerBtn = findViewById(R.id.registerBtn);
        //step 2 set on click method
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //step 3 create on intent
                Intent intent2 = new Intent(MainActivity.this,MainActivity3.class);
                //step3 start activity
                startActivity(intent2);
            }
        });
    }


}