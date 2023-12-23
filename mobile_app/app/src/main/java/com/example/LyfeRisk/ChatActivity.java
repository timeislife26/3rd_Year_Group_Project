package com.example.LyfeRisk;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // Makes the app in portrait mode only
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        FloatingActionButton chatSend = findViewById(R.id.chatSendBtn);
        TextView userMessage = findViewById(R.id.userMessage);
        TextView botMessage = findViewById(R.id.botMessage);
        EditText inputMessage = findViewById(R.id.inputET);

        chatSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userMessage.setText(inputMessage.getText());
                botMessage.setText("Loading...");
                JSONObject chatMessage = new JSONObject();
                try {
                    chatMessage.put("message", inputMessage.getText());

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                String docBotURL = "https://on-request-docbot-ef42g3nnla-uc.a.run.app";
                RequestQueue queue = Volley.newRequestQueue(ChatActivity.this);

                StringRequest docBotRequest = new StringRequest(Request.Method.POST, docBotURL, response -> {
                    Log.d("CloudFunctionResponse", response);
                    botMessage.setText(response);
                }, error -> {
                    // Handle error
                    Log.e("CloudFunctionError", "Error sending data to Cloud Function: " + error.toString());
                }) {
                    @Override
                    public byte[] getBody() {
                        return chatMessage.toString().getBytes();
                    }

                    @Override
                    public String getBodyContentType() {
                        return "application/json";
                    }
                };
                queue.add(docBotRequest);
            }
        });
    }
    public void goToContact(View view) {
        Intent intent = new Intent(ChatActivity.this, ContactActivity.class);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {

        }
    }
}